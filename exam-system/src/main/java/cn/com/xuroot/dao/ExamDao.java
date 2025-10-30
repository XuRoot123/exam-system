package cn.com.xuroot.dao;

import cn.com.xuroot.bo.ExamBo;
import cn.com.xuroot.entity.ExamPapers;
import cn.com.xuroot.entity.Exams;
import cn.com.xuroot.entity.PaperQuestions;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

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
}
