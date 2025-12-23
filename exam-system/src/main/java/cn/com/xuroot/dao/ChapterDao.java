package cn.com.xuroot.dao;

import cn.com.xuroot.entity.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChapterDao {

    @Select("select chapter_id from chapters")
    List<Integer> getAllChapterId();

    @Select("select * from chapters")
    List<Chapter> getAllChapter();
}
