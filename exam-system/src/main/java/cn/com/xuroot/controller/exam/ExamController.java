package cn.com.xuroot.controller.exam;


import cn.com.xuroot.bo.ExamBo;
import cn.com.xuroot.common.pojo.ResponseResult;
import cn.com.xuroot.service.ExamService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exam")
@Tag(name = "考试模块")
public class ExamController {

    @Autowired
    private ExamService examService;
    @PostMapping("/addRandomTestPaper")
    public ResponseResult<String> addRandomTestPaper(@RequestBody ExamBo examBo) {
        int resultCount = examService.addRandomTestPaper(examBo);
        return resultCount > 0 ? ResponseResult.success("添加成功") : ResponseResult.error(-1, "添加失败");
    }
}
