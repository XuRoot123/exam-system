package cn.com.xuroot.service.impl;

import cn.com.xuroot.bo.ExamBo;
import cn.com.xuroot.dao.ExamDao;
import cn.com.xuroot.dao.QuestionsDao;
import cn.com.xuroot.entity.ExamPapers;
import cn.com.xuroot.entity.Exams;
import cn.com.xuroot.entity.PaperQuestions;
import cn.com.xuroot.entity.Questions;
import cn.com.xuroot.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.DocFlavor;
import java.awt.print.Paper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamDao examDao;

    @Autowired
    private QuestionsDao questionsDao;

    @Override
    @Transactional
    public int addRandomTestPaper(ExamBo examBo) {
        List<Questions> questions = this.randomTitle(examBo);
        int score = 0;
        for (Questions question : questions) {
            score += question.getBase_score();
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
        }else{
            ExamPapers examPapersById = examDao.getExamPapersById(examBo.getPaperId());
            int resultExamsCount = this.addExams(examPapersById , examBo);
            if (resultExamsCount > 0){
                return this.addPaperQuestions(questions, examBo.getPaperId()) >0? 1: -1;
            }else{
                return -1;
            }
        }
    }

    @Transactional
    public int addExams(ExamPapers examPapers , ExamBo examBo){
        Exams exams = new Exams();
        exams.setPaperId(examBo.getPaperId());
        exams.setClassId(examBo.getClassId());
        exams.setStartTime(examBo.getStartTime());
        exams.setEndTime(examBo.getEndTime());
        if (exams.getStartTime().getTime() > System.currentTimeMillis()){
            exams.setStatus("pending");
        }
        else if (exams.getStartTime().getTime() < System.currentTimeMillis() && exams.getStartTime().getTime() > System.currentTimeMillis()){
            exams.setStatus("ongoing");
        }else if (exams.getStartTime().getTime() < System.currentTimeMillis())
            exams.setStatus("completed");
        exams.setInstructions(examBo.getExamInstruct());
        int resultExamsCount = examDao.addExams(exams);
        return resultExamsCount;
    }
    @Transactional
    public int addPaperQuestions(List<Questions> questionsList , int paperId){
        PaperQuestions paperQuestions = new PaperQuestions();
        paperQuestions.setPaperId(paperId);
        for (int i = 0; i < questionsList.size(); i++) {
            Questions question = questionsList.get(i);
            paperQuestions.setQuestionId(question.getQuestion_id());
            paperQuestions.setQuestionScore(question.getBase_score());
            paperQuestions.setQuestionOrder(i+1);
            int i1 = examDao.addPaperQuestions(paperQuestions);
            if (i1 < 0){
                return -1;
            }
        }
        return 1;
    }
    @Transactional
    public List<Questions> randomTitle(ExamBo examBo){
        List<Questions> questionsList = new ArrayList<>();
        questionsList.addAll(questionsDao.getRandomTitleSingleChoice(examBo));
        questionsList.addAll(questionsDao.getRandomTitleMultipleChoice(examBo));
        questionsList.addAll(questionsDao.getRandomTitleJudge(examBo));
        return questionsList;
    }
}
