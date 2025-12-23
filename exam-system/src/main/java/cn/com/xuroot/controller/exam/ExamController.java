package cn.com.xuroot.controller.exam;


import cn.com.xuroot.bo.ExamBo;
import cn.com.xuroot.common.pojo.ResponseResult;
import cn.com.xuroot.service.ExamService;
import cn.com.xuroot.vo.exam.ExamHistoryVo;
import cn.com.xuroot.vo.exam.ExamsVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam")
@Tag(name = "考试模块")
public class ExamController {

    @Autowired
    private ExamService examService;
    @PostMapping("/addRandomTestPaper")
    @Operation(summary = "添加随机试卷")
    public ResponseResult<String> addRandomTestPaper(@RequestBody ExamBo examBo) {
        int resultCount = examService.addRandomTestPaper(examBo);
        return resultCount > 0 ? ResponseResult.success("添加成功") : ResponseResult.error(-1, "添加失败");
    }
    @PostMapping("/addTestPaper")
    @Operation(summary = "添加指定试卷")
    public ResponseResult<String> addTestPaper(@RequestBody ExamBo examBo) {
        int resultCount = examService.addTestPaper(examBo);
        return resultCount > 0 ? ResponseResult.success("添加成功") : ResponseResult.error(-1, "添加失败");
    }
    @GetMapping("/getAllTestPaper")
    @Operation(summary = "获取所有考试")
    public ResponseResult<List<ExamsVo>> getAllTestPaper() {
        List<ExamsVo> allTestPaper = examService.getAllTestPaper();
        return allTestPaper != null ? ResponseResult.success(allTestPaper) : ResponseResult.error(-1, "服务器出错！");
    }
    @DeleteMapping("/deleteTestPaper")
    @Operation(summary = "删除考试")
    public ResponseResult<String> deleteTestPaper(@RequestParam Integer paperId) {
        int resultCount = examService.deleteTestPaper(paperId);
        return resultCount > 0 ? ResponseResult.success("删除成功") : ResponseResult.error(-1, "删除失败");
    }
    @GetMapping("/getTestPaperByChapterId")
    @Operation(summary = "获取指定章节的考试")
    public ResponseResult<List<ExamsVo>> getTestPaperByChapterId(@RequestParam Integer chapterId , @RequestParam Integer classId) {
        List<ExamsVo> allTestPaper = examService.getTestPaperByChapterId(chapterId, classId);
        return allTestPaper != null ? ResponseResult.success(allTestPaper) : ResponseResult.error(-1, "服务器出错！");
    }
    @GetMapping("/getAllTestPaperByClassId")
    @Operation(summary = "获取指定班级的考试")
    public ResponseResult<List<ExamsVo>> getAllTestPaperByClassId(@RequestParam Integer classId) {
        List<ExamsVo> allTestPaper = examService.getTestPaperByChapterId(null, classId);
        return allTestPaper != null ? ResponseResult.success(allTestPaper) : ResponseResult.error(-1, "服务器出错！");
    }
    @GetMapping("/getExamByExamId")
    @Operation(summary = "获取指定考试")
    public ResponseResult<ExamsVo> getExamByExamId(@RequestParam Integer examId) {
        ExamsVo exam = examService.getExamByExamId(examId);
        return exam != null ? ResponseResult.success(exam) : ResponseResult.error(-1, "服务器出错！");
    }
    @GetMapping("/getExamHistoryByChapterAndUserId")
    @Operation(summary = "获取指定章节指定用户的考试历史")
    public ResponseResult<List<ExamHistoryVo>> getExamHistoryByChapterAndUserId(@RequestParam Integer userId , @RequestParam Integer chapterId) {
        List<ExamHistoryVo> examHistory = examService.getExamHistoryByChapterAndUserId(userId, chapterId);
        return examHistory != null ? ResponseResult.success(examHistory) : ResponseResult.error(-1, "服务器出错！");
    }
    @GetMapping("/getExamHistoryByUserId")
    @Operation(summary = "获取指定用户的考试历史")
    public ResponseResult<List<ExamHistoryVo>> getExamHistoryByUserId(@RequestParam Integer userId) {
        List<ExamHistoryVo> examHistory = examService.getExamHistoryByUserId(userId);
        return examHistory != null ? ResponseResult.success(examHistory) : ResponseResult.error(-1, "服务器出错！");
    }

}
