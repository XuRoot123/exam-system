package cn.com.xuroot.dao;

import cn.com.xuroot.entity.Scores;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScoresDao {
    @Insert("INSERT INTO scores (exam_id, student_id, total_score, objective_score, subjective_score, ranking, evaluated_at) VALUES (#{examId}, #{studentId}, #{totalScore}, #{objectiveScore}, #{subjectiveScore}, #{ranking}, #{evaluatedAt})")
    int addScores(Scores scores);

    @Select("SELECT * FROM scores WHERE student_id = #{userId}")
    List<Scores> getScoresByUserId(Integer userId);
}
