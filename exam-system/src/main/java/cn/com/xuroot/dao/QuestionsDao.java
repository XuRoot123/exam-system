package cn.com.xuroot.dao;

import cn.com.xuroot.bo.ExamBo;
import cn.com.xuroot.entity.Questions;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Map;

@Mapper
public interface QuestionsDao {

    @Insert("insert into questions(chapter_id,content,type,options,answer,base_score,explanation,difficulty,created_by,created_at) values(#{chapterId},#{content},#{type},#{options},#{answer},#{baseScore},#{explanation},#{difficulty},#{createdBy},#{createdAt})")
    Integer addTitle(Questions  questions);

    List<Questions> getRandomTitleSingleChoice(ExamBo examBo);

    List<Questions> getRandomTitleMultipleChoice(ExamBo examBo);

    List<Questions> getRandomTitleJudge(ExamBo examBo);

    List<Questions> getAllTitleToType(String type);

    @Delete("delete from questions where question_id=#{questionId}")
    int deleteTitle(Integer questionId);

    @Select("select question_id from paper_questions where paper_id=#{paperId}")
    List<Integer> getAllQuestionsToPaperId(Integer paperId);

    List<Questions> getQuestionsByIds(List<Integer> questionIds);

    @Select("select * from questions where question_id = #{questionId}")
    Questions getQuestionsByQuestionId(Integer questionId);

    @Select("SELECT question_id, COUNT(*) as count FROM paper_questions GROUP BY question_id ORDER BY count DESC LIMIT #{limit}")
    List<Map<String, Object>> getHotQuestions(@Param("limit") int limit);
}
