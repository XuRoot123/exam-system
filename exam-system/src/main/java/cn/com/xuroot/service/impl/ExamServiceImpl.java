package cn.com.xuroot.service.impl;

import cn.com.xuroot.bo.ExamBo;
import cn.com.xuroot.dao.ExamDao;
import cn.com.xuroot.dao.QuestionsDao;
import cn.com.xuroot.dao.ScoresDao;
import cn.com.xuroot.entity.*;
import cn.com.xuroot.service.ExamService;
import cn.com.xuroot.vo.exam.ExamHistoryVo;
import cn.com.xuroot.vo.exam.ExamsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamDao examDao;

    @Autowired
    private QuestionsDao questionsDao;

    @Autowired
    private ScoresDao scoresDao;

    @Override
    @Transactional
    public int addRandomTestPaper(ExamBo examBo) {
        List<Questions> questions = this.randomTitle(examBo);
        int score = 0;
        for (Questions question : questions) {
            score += question.getBaseScore();
        }
        examBo.setTotalScore(score);
        if (examBo.getStartTime().getTime() > System.currentTimeMillis() && examBo.getEndTime().getTime() < System.currentTimeMillis())
            examBo.setStatus("draft");
        else {
            examBo.setStatus("published");
        }
        int examPapersCount = examDao.addExamPapers(examBo);
        if (examPapersCount <= 0) {
            return -1;
        } else {
            ExamPapers examPapersById = examDao.getExamPapersById(examBo.getPaperId());
            int resultExamsCount = this.addExams(examPapersById, examBo);
            if (resultExamsCount > 0) {
                return this.addPaperQuestions(questions, examBo.getPaperId()) > 0 ? 1 : -1;
            } else {
                return -1;
            }
        }
    }

    @Override
    public int addTestPaper(ExamBo examBo) {
        Questions[] questions = examBo.getQuestions();
        List<Questions> questionsList = new ArrayList<>(Arrays.asList(questions));
        Collections.shuffle(questionsList);
        int score = 0;
        for (Questions question : questionsList) {
            score += question.getBaseScore();
        }
        examBo.setTotalScore(score);
        if (examBo.getStartTime().getTime() > System.currentTimeMillis() && examBo.getEndTime().getTime() < System.currentTimeMillis())
            examBo.setStatus("draft");
        else {
            examBo.setStatus("published");
        }
        int examPapersCount = examDao.addExamPapers(examBo);
        if (examPapersCount <= 0) {
            return -1;
        } else {
            ExamPapers examPapersById = examDao.getExamPapersById(examBo.getPaperId());
            int resultExamsCount = this.addExams(examPapersById, examBo);
            if (resultExamsCount > 0) {
                return this.addPaperQuestions(questionsList, examBo.getPaperId()) > 0 ? 1 : -1;
            } else {
                return -1;
            }
        }
    }

    @Override
    public List<ExamsVo> getAllTestPaper() {
        return examDao.getAllTestPaper();
    }

    @Override
    @Transactional
    public int deleteTestPaper(Integer paperId) {
        try {
            examDao.deletePaperQuestions(paperId);
            examDao.deleteExamPapers(paperId);
            examDao.deleteExams(paperId);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public List<ExamsVo> getTestPaperByChapterId(Integer chapterId, Integer classId) {
        List<ExamsVo> testPaperByChapterId = examDao.getTestPaperByChapterId(chapterId, classId);
        for (int i = 0; i < testPaperByChapterId.size(); i++) {
            if (testPaperByChapterId.get(i).getStatus().equals("completed")) {
                testPaperByChapterId.get(i).setStatus("已完结");
            } else if (testPaperByChapterId.get(i).getStatus().equals("pending")) {
                testPaperByChapterId.get(i).setStatus("未开始");
            } else if (testPaperByChapterId.get(i).getStatus().equals("ongoing")) {
                testPaperByChapterId.get(i).setStatus("进行中");
            }
        }
        return testPaperByChapterId;
    }

    @Override
    public ExamsVo getExamByExamId(Integer examId) {
        ExamsVo examsVo = examDao.getExamByExamId(examId);
        examsVo.setStatus("已考完");
        return examsVo;
    }

    @Override
    public List<ExamHistoryVo> getExamHistoryByChapterAndUserId(Integer userId, Integer chapterId) {
        List<ExamHistoryVo> examHistoryVos = new ArrayList<>();
        List<Scores> scoresByUserId = scoresDao.getScoresByUserId(userId);
        if (scoresByUserId.isEmpty())
            return examHistoryVos;
        List<Integer> tempExamsId = new ArrayList<>();
        List<Integer> tempActualScore = new ArrayList<>();
        List<Integer> tempTotalScore = new ArrayList<>();
        List<String> tempExamsAt = new ArrayList<>();
        List<String> tempPaperNames = new ArrayList<>();
        for (int i = 0; i < scoresByUserId.size(); i++) {
            Integer examId = scoresByUserId.get(i).getExamId();
            if (!tempExamsId.contains(examId)) {
                tempExamsId.add(examId);
                ExamsVo examByExamId = examDao.getExamByExamIdAndChapterId(tempExamsId.get(i), chapterId);
                tempPaperNames.add(examByExamId.getPaperName());
                tempExamsAt.add(examByExamId.getCreatedAt());
                Integer totalScore = examByExamId.getTotalScore();
                tempTotalScore.add(totalScore);
            }
            tempActualScore.add((scoresByUserId.get(i).getObjectiveScore() + scoresByUserId.get(i).getSubjectiveScore()));
        }
        for (int i = 0; i < tempExamsId.size(); i++) {
            ExamHistoryVo examHistoryVo = new ExamHistoryVo();
            examHistoryVo.setUserId(userId);
            examHistoryVo.setExamId(tempExamsId.get(i));
            examHistoryVo.setPaperName(tempPaperNames.get(i));
            examHistoryVo.setTotalScore(tempTotalScore.get(i));
            examHistoryVo.setActualScore(tempActualScore.get(i));
            examHistoryVo.setExamAt(tempExamsAt.get(i));
            examHistoryVos.add(examHistoryVo);
        }
        return examHistoryVos;
    }
    @Override
    public List<ExamHistoryVo> getExamHistoryByUserId(Integer userId) {
        List<ExamHistoryVo> examHistoryVos = new ArrayList<>();
        List<Scores> scoresByUserId = scoresDao.getScoresByUserId(userId);
        if (scoresByUserId.isEmpty())
            return examHistoryVos;
        List<Integer> tempExamsId = new ArrayList<>();
        List<Integer> tempActualScore = new ArrayList<>();
        List<Integer> tempTotalScore = new ArrayList<>();
        List<String> tempExamsAt = new ArrayList<>();
        List<String> tempPaperNames = new ArrayList<>();
        for (int i = 0; i < scoresByUserId.size(); i++) {
            Integer examId = scoresByUserId.get(i).getExamId();
            if (!tempExamsId.contains(examId)) {
                tempExamsId.add(examId);
                ExamsVo examByExamId = examDao.getExamByExamId(tempExamsId.get(i));
                tempPaperNames.add(examByExamId.getPaperName());
                tempExamsAt.add(examByExamId.getCreatedAt());
                Integer totalScore = examByExamId.getTotalScore();
                tempTotalScore.add(totalScore);
            }
            tempActualScore.add((scoresByUserId.get(i).getObjectiveScore() + scoresByUserId.get(i).getSubjectiveScore()));
        }
        for (int i = 0; i < tempExamsId.size(); i++) {
            ExamHistoryVo examHistoryVo = new ExamHistoryVo();
            examHistoryVo.setUserId(userId);
            examHistoryVo.setExamId(tempExamsId.get(i));
            examHistoryVo.setPaperName(tempPaperNames.get(i));
            examHistoryVo.setTotalScore(tempTotalScore.get(i));
            examHistoryVo.setActualScore(tempActualScore.get(i));
            examHistoryVo.setExamAt(tempExamsAt.get(i));
            examHistoryVos.add(examHistoryVo);
        }
        return examHistoryVos;
    }

    @Transactional
    public int addExams(ExamPapers examPapers, ExamBo examBo) {
        Exams exams = new Exams();
        exams.setPaperId(examBo.getPaperId());
        exams.setClassId(examBo.getClassId());
        exams.setStartTime(examBo.getStartTime());
        exams.setEndTime(examBo.getEndTime());
        long currentTime = System.currentTimeMillis();
        long startTime = exams.getStartTime().getTime();
        long endTime = exams.getEndTime().getTime();
        if (startTime > currentTime) {
            exams.setStatus("pending");
        } else if (endTime >= currentTime) {
            exams.setStatus("ongoing");
        } else {
            exams.setStatus("completed");
        }
        exams.setInstructions(examBo.getExamInstruct());
        return examDao.addExams(exams);
    }

    @Transactional
    public int addPaperQuestions(List<Questions> questionsList, int paperId) {
        PaperQuestions paperQuestions = new PaperQuestions();
        paperQuestions.setPaperId(paperId);
        for (int i = 0; i < questionsList.size(); i++) {
            Questions question = questionsList.get(i);
            paperQuestions.setQuestionId(question.getQuestionId());
            paperQuestions.setQuestionScore(question.getBaseScore());
            paperQuestions.setQuestionOrder(i + 1);
            int i1 = examDao.addPaperQuestions(paperQuestions);
            if (i1 < 0) {
                return -1;
            }
        }
        return 1;
    }

    @Transactional
    public List<Questions> randomTitle(ExamBo examBo) {
        List<Questions> questionsList = new ArrayList<>();
        questionsList.addAll(questionsDao.getRandomTitleSingleChoice(examBo));
        questionsList.addAll(questionsDao.getRandomTitleMultipleChoice(examBo));
        questionsList.addAll(questionsDao.getRandomTitleJudge(examBo));
        return questionsList;
    }
}
