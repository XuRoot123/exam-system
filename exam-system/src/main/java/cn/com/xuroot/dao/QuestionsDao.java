package cn.com.xuroot.dao;

import cn.com.xuroot.bo.ExamBo;
import cn.com.xuroot.entity.Questions;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionsDao {

    @Insert("insert into questions(chapter_id,content,type,options,answer,base_score,explanation,difficulty,created_by,created_at) values(#{chapter_id},#{content},#{type},#{options},#{answer},#{base_score},#{explanation},#{difficulty},#{created_by},#{created_at})")
    Integer addTitle(Questions  questions);

    List<Questions> getRandomTitleSingleChoice(ExamBo examBo);

    List<Questions> getRandomTitleMultipleChoice(ExamBo examBo);

    List<Questions> getRandomTitleJudge(ExamBo examBo);
}
