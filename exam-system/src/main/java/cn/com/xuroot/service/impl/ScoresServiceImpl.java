package cn.com.xuroot.service.impl;

import cn.com.xuroot.dao.ScoresDao;
import cn.com.xuroot.entity.Scores;
import cn.com.xuroot.service.ScoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoresServiceImpl implements ScoresService {

    @Autowired
    private ScoresDao scoresDao;

    @Override
    public int addScores(Scores scores) {
        return scoresDao.addScores(scores);
    }

    @Override
    public List<Scores> getScoresByUserId(Integer userId) {
        return scoresDao.getScoresByUserId(userId);
    }
}
