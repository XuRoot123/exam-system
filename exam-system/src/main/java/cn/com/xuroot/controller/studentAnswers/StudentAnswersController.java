package cn.com.xuroot.controller.studentAnswers;

import cn.com.xuroot.bo.StudentAnswersBo;
import cn.com.xuroot.common.pojo.ResponseResult;
import cn.com.xuroot.service.StudentAnswersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studentAnswers")
@Tag(name="学生考试答案模块")
public class StudentAnswersController {

    @Autowired
    private StudentAnswersService studentAnswersService;

    @PostMapping("/addStudentAnswers")
    @Operation(summary = "添加学生考试答案")
    public ResponseResult<String> addStudentAnswers(@RequestBody StudentAnswersBo studentAnswersBo){
      return studentAnswersService.addStudentAnswers(studentAnswersBo) > 0 ? ResponseResult.success("提交成功") : ResponseResult.error(-1, "服务器故障！");
    }
    @GetMapping("/checkAnswerRecord")
    @Operation(summary = "检查学生考试答案记录")
    public ResponseResult<Boolean> checkAnswerRecord(@Parameter(description = "学生ID") Integer studentId , @Parameter(description = "考试ID") Integer examId){
        return ResponseResult.success(studentAnswersService.checkAnswerRecord(studentId, examId));
    }
}
