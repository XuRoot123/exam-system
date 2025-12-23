package cn.com.xuroot.dao;

import cn.com.xuroot.entity.StudentAnswers;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentAnswersDao {

    @Insert("INSERT INTO student_answers(exam_id, student_id, question_id, answer, is_correct, is_reviewed, score, reviewed_by, review_notes, created_at) VALUES(#{examId}, #{studentId}, #{questionId}, #{answer}, #{isCorrect}, #{isReviewed}, #{score}, #{reviewedBy}, #{reviewNotes}, #{createdAt})")
    int addStudentAnswers(StudentAnswers studentAnswers);

    @Select("SELECT COUNT(*) FROM student_answers WHERE student_id = #{studentId} AND exam_id = #{examId}")
    int checkAnswerRecord(Integer studentId, Integer examId);
}
