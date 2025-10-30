package cn.com.xuroot.service;

import cn.com.xuroot.entity.Questions;

import java.util.List;

public interface QuestionsService {
    List<Integer> getAllChapterId();
    Integer addTitle(Questions questions);
}
