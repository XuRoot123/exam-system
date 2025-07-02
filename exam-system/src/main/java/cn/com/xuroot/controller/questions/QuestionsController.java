package cn.com.xuroot.controller.questions;

import cn.com.xuroot.common.pojo.ResponseResult;
import cn.com.xuroot.service.QuestionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/questions")
@Tag(name = "题库模块")
public class QuestionsController {
    @Autowired
    private QuestionsService questionsService;

    @GetMapping("/getAllChapterId")
    @Operation(summary = "获取所有章节ID")
    public ResponseResult<List<Integer>> getAllChapterId(){
        List<Integer> allChapterId = questionsService.getAllChapterId();
        if(allChapterId != null)
            return ResponseResult.success(allChapterId);
        return ResponseResult.error(-1,"服务器故障，请联系管理员！");
    };
}
