package cn.com.xuroot.service;

import cn.com.xuroot.entity.Scores;

import java.util.List;

public interface ScoresService {
    int addScores(Scores scores);
    List<Scores> getScoresByUserId(Integer userId);
}
