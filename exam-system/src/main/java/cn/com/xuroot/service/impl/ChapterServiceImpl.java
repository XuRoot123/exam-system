package cn.com.xuroot.service.impl;

import cn.com.xuroot.dao.ChapterDao;
import cn.com.xuroot.entity.Chapter;
import cn.com.xuroot.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterDao chapterDao;

    @Override
    public List<Chapter> getAllChapter() {
        return chapterDao.getAllChapter();
    }
}
