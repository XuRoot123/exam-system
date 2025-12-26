package cn.com.xuroot.service;

import cn.com.xuroot.bo.WrongAnswersBo;
import cn.com.xuroot.entity.WrongAnswers;

import java.util.List;

public interface WrongAnswersService {
    int addWrongAnswers(WrongAnswersBo wrongAnswersBo);

    List<WrongAnswers> getWrongAnswersByUserIdAndExamId(Integer userId, Integer examId);
}
