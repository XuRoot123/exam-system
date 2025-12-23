package cn.com.xuroot.controller.wrongAnswers;

import cn.com.xuroot.bo.WrongAnswersBo;
import cn.com.xuroot.common.pojo.ResponseResult;
import cn.com.xuroot.service.WrongAnswersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wrongAnswers")
@Tag(name="错题模块")
public class WrongAnswersController {

    @Autowired
    private WrongAnswersService wrongAnswersService;

    @RequestMapping("/addWrongAnswers")
    @Operation(summary = "添加错题")
    public ResponseResult<String> addWrongAnswers(@RequestBody WrongAnswersBo wrongAnswersBo){
        return wrongAnswersService.addWrongAnswers(wrongAnswersBo) > 0 ? ResponseResult.success("添加成功") : ResponseResult.error(-1, "服务器故障！");
    }
}
