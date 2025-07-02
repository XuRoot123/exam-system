package cn.com.xuroot.controller.classes;

import cn.com.xuroot.common.pojo.ResponseResult;
import cn.com.xuroot.entity.Classes;
import cn.com.xuroot.service.ClassesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/classes")
@Tag(name = "班级模块")
public class ClassesController {

    @Autowired
    ClassesService classesService;

    @GetMapping("/getAllClasses")
    @Operation(summary = "获取所有班级")
    public ResponseResult<List<Classes>>getAllClasses() {
        return ResponseResult.success(classesService.getAllClasses());
    }
}
