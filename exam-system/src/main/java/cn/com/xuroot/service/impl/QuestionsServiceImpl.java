package cn.com.xuroot.service.impl;


import cn.com.xuroot.dao.ChapterDao;
import cn.com.xuroot.dao.QuestionsDao;
import cn.com.xuroot.entity.Questions;
import cn.com.xuroot.service.QuestionsService;
import cn.com.xuroot.vo.questions.HotQuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    @Autowired
    private ChapterDao chapterDao;

    @Autowired
    private QuestionsDao questionsDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String HOT_QUESTIONS_KEY = "hot:questions:rank";

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

    @Override
    public List<HotQuestionVo> showHighFrequencyExamQuestions() {
        Boolean hasKey = redisTemplate.hasKey(HOT_QUESTIONS_KEY);
        if (Boolean.TRUE.equals(hasKey)) {
            Long expireTime = redisTemplate.getExpire(HOT_QUESTIONS_KEY, TimeUnit.MILLISECONDS);
            if (expireTime != null && expireTime > 0) {
                Set<String> topMembers = redisTemplate.opsForZSet()
                        .reverseRange(HOT_QUESTIONS_KEY, 0, 9);

                if (topMembers != null && !topMembers.isEmpty()) {
                    List<HotQuestionVo> result = new ArrayList<>();
                    for (String memberId : topMembers) {
                        Integer questionId = Integer.parseInt(memberId);
                        Double score = redisTemplate.opsForZSet().score(HOT_QUESTIONS_KEY, memberId);

                        Questions question = questionsDao.getQuestionsByQuestionId(questionId);
                        if (question != null) {
                            HotQuestionVo vo = new HotQuestionVo();
                            vo.setQuestionId(question.getQuestionId());
                            vo.setContent(question.getContent());
                            vo.setType(question.getType());
                            vo.setDifficulty(question.getDifficulty());
                            vo.setChapterId(question.getChapterId());
                            vo.setAppearCount(score != null ? score.longValue() : 0L);
                            result.add(vo);
                        }
                    }
                    return result;
                }
            }
        }
        List<Map<String, Object>> hotQuestionsFromDB = questionsDao.getHotQuestions(10);
        redisTemplate.delete(HOT_QUESTIONS_KEY);
        for (Map<String, Object> item : hotQuestionsFromDB) {
            Integer questionId = ((Number) item.get("question_id")).intValue();
            Long count = ((Number) item.get("count")).longValue();
            redisTemplate.opsForZSet().add(HOT_QUESTIONS_KEY, String.valueOf(questionId), count);
        }
        redisTemplate.expire(HOT_QUESTIONS_KEY, 1, TimeUnit.DAYS);
        List<HotQuestionVo> result = new ArrayList<>();
        for (Map<String, Object> item : hotQuestionsFromDB) {
            Integer questionId = ((Number) item.get("question_id")).intValue();
            Long count = ((Number) item.get("count")).longValue();

            Questions question = questionsDao.getQuestionsByQuestionId(questionId);
            if (question != null) {
                HotQuestionVo vo = new HotQuestionVo();
                vo.setQuestionId(question.getQuestionId());
                vo.setContent(question.getContent());
                vo.setType(question.getType());
                vo.setDifficulty(question.getDifficulty());
                vo.setChapterId(question.getChapterId());
                vo.setAppearCount(count);
                result.add(vo);
            }
        }
        return result;
    }
}