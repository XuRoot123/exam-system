package cn.com.xuroot.controller.questions;

import cn.com.xuroot.common.pojo.ResponseResult;
import cn.com.xuroot.entity.Questions;
import cn.com.xuroot.service.QuestionsService;
import cn.com.xuroot.util.AesUtil;
import cn.com.xuroot.vo.questions.QuestionsVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/questions")
@Tag(name = "题库模块")
public class QuestionsController {
    @Autowired
    private QuestionsService questionsService;

    @GetMapping("/getAllChapterId")
    @Operation(summary = "获取所有章节ID")
    public ResponseResult<List<Integer>> getAllChapterId() {
        List<Integer> allChapterId = questionsService.getAllChapterId();
        if (allChapterId != null)
            return ResponseResult.success(allChapterId);
        return ResponseResult.error(-1, "服务器故障，请联系管理员！");
    }

    ;

    @PostMapping("/addTitle")
    @Operation(summary = "添加题目")
    public ResponseResult<Questions> addTitle(@RequestBody QuestionsVo questionsVo) {
        questionsVo.convertOptionsListToString();
        Questions questions = new Questions();
        questions.setQuestionId((questionsVo.getQuestionId()));
        questions.setChapterId(questionsVo.getChapterId());
        questions.setContent(questionsVo.getContent());
        questions.setType(questionsVo.getType());
        questions.setOptions(questionsVo.getOptions());
        try {
            String decodedOptions = AesUtil.decrypt(questionsVo.getAnswer());
            questions.setAnswer(decodedOptions);
        } catch (Exception e) {
            return ResponseResult.error(-1, "解密失败！");
        }
        questions.setBaseScore(questionsVo.getBaseScore());
        questions.setExplanation(questionsVo.getExplanation());
        questions.setDifficulty(questionsVo.getDifficulty());
        questions.setCreatedBy(questionsVo.getCreatedBy());
        questions.setCreatedAt(questionsVo.getCreatedAt());
        Integer i = questionsService.addTitle(questions);
        return i == 1 ? ResponseResult.success(questions) : ResponseResult.error(-1, "添加失败！");
    }

    ;

    @GetMapping("/getAllTitleToType")
    @Operation(summary = "获取所有指定类型的题目")
    public ResponseResult<List<Questions>> getAllTitleToType(@RequestParam(value = "type", required = false) @Parameter(name = "type", description = "题目类型") String type) {
        List<Questions> allTitleToType = questionsService.getAllTitleToType(type);
        if (allTitleToType != null)
            return ResponseResult.success(allTitleToType);
        return ResponseResult.error(-1, "服务器故障，请联系管理员！");
    }

    ;

    @DeleteMapping("/deleteTitle")
    @Operation(summary = "删除题目")
    public ResponseResult<String> deleteTitle(@RequestParam(value = "questionId") @Parameter(name = "questionId", description = "题目ID") Integer questionId) {
        int i = questionsService.deleteTitle(questionId);
        return i == 1 ? ResponseResult.success("删除成功") : ResponseResult.error(-1, "服务器出错！");
    }

    ;

    @GetMapping("/getAllQuestionByPaperId")
    @Operation(summary = "获取所有指定试卷的题目")
    public ResponseResult<List<Questions>> getAllQuestionsToPaperId(@RequestParam(value = "paperId") @Parameter(name = "paperId", description = "试卷ID") Integer paperId) {
        List<Questions> allQuestionsToPaperId = questionsService.getAllQuestionsToPaperId(paperId);
        if (allQuestionsToPaperId != null) {
            for (Questions question : allQuestionsToPaperId) {
                try {
                    String encryptedAnswer = AesUtil.encrypt(question.getAnswer());
                    question.setAnswer(encryptedAnswer);
                } catch (Exception e) {
                    return ResponseResult.error(-1, "答案加密失败！");
                }
            }
            return ResponseResult.success(allQuestionsToPaperId);
        }
        return ResponseResult.error(-1, "服务器故障，请联系管理员！");
    }
    @GetMapping("/getQuestionsByQuestionId")
    @Operation(summary = "获取指定题目")
    public ResponseResult<Questions> getQuestionsByQuestionId(@RequestParam(value = "questionId") @Parameter(name = "questionId", description = "题目ID") Integer questionId) {
        Questions questions = questionsService.getQuestionsByQuestionId(questionId);
        if (questions != null) {
            try {
                String encryptedAnswer = AesUtil.encrypt(questions.getAnswer());
                questions.setAnswer(encryptedAnswer);
            } catch (Exception e) {
                return ResponseResult.error(-1, "答案加密失败！");
            }
        }
        return questions != null ? ResponseResult.success(questions) : ResponseResult.error(-1, "服务器故障，请联系管理员！");
    }
}
