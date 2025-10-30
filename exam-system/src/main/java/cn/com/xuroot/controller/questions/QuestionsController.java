package cn.com.xuroot.controller.questions;

import cn.com.xuroot.common.pojo.ResponseResult;
import cn.com.xuroot.entity.Questions;
import cn.com.xuroot.service.QuestionsService;
import cn.com.xuroot.vo.questions.QuestionsVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addTitle")
    @Operation(summary = "添加题目")
    public ResponseResult<Questions> addTitle(@RequestBody QuestionsVo questionsVo){
        questionsVo.convertOptionsListToString();
        Questions questions = new Questions();
        questions.setQuestion_id(questionsVo.getQuestion_id());
        questions.setChapter_id(questionsVo.getChapter_id());
        questions.setContent(questionsVo.getContent());
        questions.setType(questionsVo.getType());
        questions.setOptions(questionsVo.getOptions());
        questions.setAnswer(questionsVo.getAnswer());
        questions.setBase_score(questionsVo.getBase_score());
        questions.setExplanation(questionsVo.getExplanation());
        questions.setDifficulty(questionsVo.getDifficulty());
        questions.setCreated_by(questionsVo.getCreated_by());
        questions.setCreated_at(questionsVo.getCreated_at());
        Integer i = questionsService.addTitle(questions);
        return i == 1 ? ResponseResult.success(questions) : ResponseResult.error(-1,"添加失败！");
    };
}
