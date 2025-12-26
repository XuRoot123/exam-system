package cn.com.xuroot.service.impl;

import cn.com.xuroot.bo.WrongAnswersBo;
import cn.com.xuroot.dao.WrongAnswersDao;
import cn.com.xuroot.entity.WrongAnswers;
import cn.com.xuroot.service.WrongAnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WrongAnswersServiceImpl implements WrongAnswersService {

    @Autowired
    private WrongAnswersDao wrongAnswersDao;
    @Override
    public int addWrongAnswers(WrongAnswersBo wrongAnswersBo) {
        int count = 0;
        for (int i = 0; i < wrongAnswersBo.getQuestionsId().length; i++) {
            WrongAnswers wrongAnswers = new WrongAnswers();
            wrongAnswers.setStudentId(wrongAnswersBo.getStudentId());
            wrongAnswers.setQuestionId(wrongAnswersBo.getQuestionsId()[i]);
            wrongAnswers.setExamId(wrongAnswersBo.getExamId());
            wrongAnswers.setWrongAnswer(wrongAnswersBo.getWrongAnswers()[i]);
            wrongAnswers.setCorrectAnswer(wrongAnswersBo.getCorrectAnswers()[i]);
            wrongAnswers.setCollectedAt(new Date());
            count += wrongAnswersDao.addWrongAnswers(wrongAnswers);
        }
        return count == wrongAnswersBo.getQuestionsId().length ? 1 : 0;
    }

    @Override
    public List<WrongAnswers> getWrongAnswersByUserIdAndExamId(Integer userId, Integer examId) {
        return wrongAnswersDao.getWrongAnswersByUserIdAndExamId(userId, examId);
    }
}
