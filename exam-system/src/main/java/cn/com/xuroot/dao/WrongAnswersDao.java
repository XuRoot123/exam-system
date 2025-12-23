package cn.com.xuroot.dao;

import cn.com.xuroot.entity.WrongAnswers;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WrongAnswersDao {

    @Insert("INSERT INTO wrong_answers (student_id, question_id, exam_id, wrong_answer, correct_answer, collected_at) VALUES (#{studentId}, #{questionId}, #{examId}, #{wrongAnswer}, #{correctAnswer}, #{collectedAt});")
    int addWrongAnswers(WrongAnswers wrongAnswers);
}
