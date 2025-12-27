package cn.com.xuroot.controller.undetermineduser;

import cn.com.xuroot.common.pojo.PageEntity;
import cn.com.xuroot.common.pojo.ResponseResult;
import cn.com.xuroot.entity.UndeterminedUser;
import cn.com.xuroot.service.UndeterminedUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/undeterminedUser")
@Tag(name = "待定用户模块")
public class UndeterminedUserController {

    @Autowired
    private UndeterminedUserService undeterminedUserService;

    @GetMapping("/teacher/getAllUndeterminedUserPage")
    @Operation(summary = "获取所有待定用户")
    public ResponseResult<PageEntity<UndeterminedUser>> getAllUndeterminedUserPage(
            @Parameter(name = "realName", description = "真实名字，可通过模糊查询查找")
            @RequestParam(required = false)
            String realName,
            @Parameter(name = "role", description = "角色")
            @RequestParam(required = false)
            String role,
            @Parameter(name = "pageIndex", description = "页码")
            @RequestParam
            Integer pageIndex,
            @Parameter(name = "pageSize", description = "每页数量")
            @RequestParam
            Integer pageSize,
            @Parameter(name = "userId", description = "用户id")
            @RequestParam
            Integer userId
    ) {
        PageEntity<UndeterminedUser> undeterminedUserPageEntity =
                undeterminedUserService.getAllUndeterminedUserPage
                        (realName, role, pageIndex, pageSize, "teacher", userId);
        if (undeterminedUserPageEntity != null) {
            return ResponseResult.success(undeterminedUserPageEntity);
        }
        return ResponseResult.success(null);
    }

    @DeleteMapping("/deleteUndeterminedUser")
    @Operation(summary = "删除待定用户")
    public ResponseResult<Integer> deleteUndeterminedUser(
            @Parameter(name = "userId", description = "用户id")
            @RequestParam
            Integer userId
    ) {
        Integer result = undeterminedUserService.deleteUndeterminedUser(userId);
        if (result > 0) {
            return ResponseResult.success(result);
        }
        return ResponseResult.success(null);
    }
}