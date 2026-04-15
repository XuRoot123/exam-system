package cn.com.xuroot.service;

import cn.com.xuroot.entity.Questions;
import cn.com.xuroot.vo.questions.HotQuestionVo;

import java.util.List;

public interface QuestionsService {
    List<Integer> getAllChapterId();

    Integer addTitle(Questions questions);

    List<Questions> getAllTitleToType(String type);

    int deleteTitle(Integer questionId);

    List<Questions> getAllQuestionsToPaperId(Integer paperId);

    Questions getQuestionsByQuestionId(Integer questionId);

    List<HotQuestionVo> showHighFrequencyExamQuestions();
}
