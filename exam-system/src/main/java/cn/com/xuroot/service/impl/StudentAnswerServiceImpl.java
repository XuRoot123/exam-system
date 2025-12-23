package cn.com.xuroot.service.impl;

import cn.com.xuroot.bo.StudentAnswersBo;
import cn.com.xuroot.dao.StudentAnswersDao;
import cn.com.xuroot.entity.StudentAnswers;
import cn.com.xuroot.service.StudentAnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class StudentAnswerServiceImpl implements StudentAnswersService {

    @Autowired
    private StudentAnswersDao studentAnswersDao;

    @Override
    public int addStudentAnswers(StudentAnswersBo studentAnswersBo) {
        int count = 0;
        for (int i = 0; i < studentAnswersBo.getAnswers().length; i++) {
            StudentAnswers studentAnswers = new StudentAnswers();
            studentAnswers.setExamId(studentAnswersBo.getExamId());
            studentAnswers.setStudentId(studentAnswersBo.getStudentId());
            studentAnswers.setQuestionId(studentAnswersBo.getQuestionsId()[i]);
            studentAnswers.setAnswer(studentAnswersBo.getAnswers()[i]);
            studentAnswers.setIsCorrect(studentAnswersBo.getIsCorrect()[i]);
            studentAnswers.setIsReviewed(studentAnswersBo.getIsReviewed());
            studentAnswers.setScore(studentAnswersBo.getScore()[i]);
            studentAnswers.setReviewedBy(studentAnswersBo.getReviewedBy());
            studentAnswers.setReviewNotes(studentAnswersBo.getReviewNotes());
            studentAnswers.setCreatedAt(new Date());
            count += studentAnswersDao.addStudentAnswers(studentAnswers);
        }
        return count == studentAnswersBo.getAnswers().length ? 1 : 0;
    }

    @Override
    public boolean checkAnswerRecord(Integer studentId, Integer examId) {
        return studentAnswersDao.checkAnswerRecord(studentId, examId) <= 0;
    }
}