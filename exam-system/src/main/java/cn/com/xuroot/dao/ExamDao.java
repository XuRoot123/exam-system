package cn.com.xuroot.dao;

import cn.com.xuroot.bo.ExamBo;
import cn.com.xuroot.entity.ExamPapers;
import cn.com.xuroot.entity.Exams;
import cn.com.xuroot.entity.PaperQuestions;
import cn.com.xuroot.vo.exam.ExamsVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExamDao {

    @Options(useGeneratedKeys = true, keyProperty = "paperId")
    @Insert("insert into exampapers(paper_name,teacher_id,chapter_id,total_score,duration_min,status,created_at) values(#{examName},#{createId},#{chapterId},#{totalScore},#{testExamTime},#{status},#{createTime})")
    int addExamPapers(ExamBo examBo);

    @Select("select * from exampapers where paper_id = #{paperId}")
    ExamPapers getExamPapersById(Integer paperId);

    @Insert("insert into exams(exam_id,paper_id,class_id,start_time,end_time,status,instructions) values(#{examId},#{paperId},#{classId},#{startTime},#{endTime},#{status},#{instructions})")
    int addExams(Exams exams);

    @Insert("insert into paper_questions(paper_id,question_id,question_order,question_score) values(#{paperId},#{questionId},#{questionOrder},#{questionScore})")
    int addPaperQuestions(PaperQuestions paperQuestions);

    @Select("SELECT e.exam_id AS examId, e.paper_id AS paperId, ep.paper_name AS paperName, e.class_id AS classId, e.start_time AS startTime, e.end_time AS endTime, e.status AS status, e.instructions AS instructions, ep.created_at AS createdAt, ep.total_score AS totalScore, ep.duration_min AS testExamTime, ep.chapter_id AS chapterId, u.username AS teacherName FROM exams e LEFT JOIN exampapers ep ON e.paper_id = ep.paper_id LEFT JOIN users u ON u.user_id = ep.teacher_id")
    List<ExamsVo> getAllTestPaper();

    List<ExamsVo> getTestPaperByChapterId(Integer chapterId , Integer classId);

    @Delete("DELETE FROM paper_questions WHERE paper_id = #{paperId}")
    int deletePaperQuestions(Integer paperId);

    @Delete("DELETE FROM exams WHERE paper_id = #{paperId}")
    int deleteExams(Integer paperId);

    @Delete("DELETE FROM exampapers WHERE paper_id = #{paperId}")
    int deleteExamPapers(Integer paperId);

    @Select("SELECT e.exam_id AS examId, e.paper_id AS paperId, ep.paper_name AS paperName, e.class_id AS classId, e.start_time AS startTime, e.end_time AS endTime, e.status AS status, e.instructions AS instructions, ep.created_at AS createdAt, ep.total_score AS totalScore, ep.duration_min AS testExamTime, ep.chapter_id AS chapterId, u.username AS teacherName FROM exams e LEFT JOIN exampapers ep ON e.paper_id = ep.paper_id LEFT JOIN users u ON u.user_id = ep.teacher_id WHERE e.exam_id = #{examId}")
    ExamsVo getExamByExamId(Integer examId);

    ExamsVo getExamByExamIdAndChapterId(Integer examId , Integer chapterId);

}
