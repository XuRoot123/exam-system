package cn.com.xuroot.controller.scores;


import cn.com.xuroot.common.pojo.ResponseResult;
import cn.com.xuroot.entity.Scores;
import cn.com.xuroot.service.ScoresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scores")
@Tag(name = "成绩模块")
public class ScoresController {

    @Autowired
    private ScoresService scoresService;

    @PostMapping("addScores")
    @Operation(summary = "添加成绩")
    public ResponseResult<String> addScores(@RequestBody Scores scores) {
        return scoresService.addScores(scores) >=0 ? ResponseResult.success("添加成功") : ResponseResult.error(-1, "添加失败");
    }

    @GetMapping("getScoresByUserId")
    @Operation(summary = "获取指定用户的成绩")
    public ResponseResult<List<Scores>> getScoresByUserId(@RequestParam Integer userId) {
        return ResponseResult.success(scoresService.getScoresByUserId(userId));
    }
}
