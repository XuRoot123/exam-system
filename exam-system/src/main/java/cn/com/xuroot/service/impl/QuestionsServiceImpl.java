package cn.com.xuroot.service.impl;

import cn.com.xuroot.dao.ChapterDao;
import cn.com.xuroot.dao.QuestionsDao;
import cn.com.xuroot.entity.Questions;
import cn.com.xuroot.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    @Autowired
    private ChapterDao chapterDao;

    @Autowired
    private QuestionsDao questionsDao;
    @Override
    public List<Integer> getAllChapterId() {
        return chapterDao.getAllChapterId().stream().sorted().toList();
    }

    @Override
    public Integer addTitle(Questions questions) {
        return questionsDao.addTitle(questions);
    }

    @Override
    public List<Questions> getAllTitleToType(String type) {
        return questionsDao.getAllTitleToType(type);
    }

    @Override
    public int deleteTitle(Integer questionId) {
        return questionsDao.deleteTitle(questionId);
    }

    @Override
    public List<Questions> getAllQuestionsToPaperId(Integer paperId) {
        List<Integer> allQuestionsToPaperId = questionsDao.getAllQuestionsToPaperId(paperId);
        return questionsDao.getQuestionsByIds(allQuestionsToPaperId);
    }

    @Override
    public Questions getQuestionsByQuestionId(Integer questionId) {
        return questionsDao.getQuestionsByQuestionId(questionId);
    }
}