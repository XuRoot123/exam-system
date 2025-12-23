package cn.com.xuroot.controller.chapter;

import cn.com.xuroot.common.pojo.ResponseResult;
import cn.com.xuroot.entity.Chapter;
import cn.com.xuroot.service.ChapterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chapter")
@Tag(name = "章节模块")
public class ChapterController {

    @Autowired
    ChapterService chapterService;

    @GetMapping("/getAllChapter")
    public ResponseResult<List<Chapter>> getAllChapter() {
        return chapterService.getAllChapter()!= null ? ResponseResult.success(chapterService.getAllChapter()) : ResponseResult.error(-1, "服务器出错！");
    }
}
