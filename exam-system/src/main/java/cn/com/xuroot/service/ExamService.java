package cn.com.xuroot.service;

import cn.com.xuroot.bo.ExamBo;
import cn.com.xuroot.entity.Exams;
import cn.com.xuroot.vo.exam.ExamHistoryVo;
import cn.com.xuroot.vo.exam.ExamsVo;

import java.util.List;

public interface ExamService {
    int addRandomTestPaper(ExamBo examBo);

    int addTestPaper(ExamBo examBo);

    List<ExamsVo> getAllTestPaper();

    int deleteTestPaper(Integer paperId);

    List<ExamsVo> getTestPaperByChapterId(Integer chapterId, Integer classId);

    ExamsVo getExamByExamId(Integer examId);

    List<ExamHistoryVo> getExamHistoryByChapterAndUserId(Integer userId , Integer chapterId);

    List<ExamHistoryVo> getExamHistoryByUserId(Integer userId);
}
