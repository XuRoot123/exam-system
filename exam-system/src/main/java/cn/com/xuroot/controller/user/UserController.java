package cn.com.xuroot.controller.user;

import cn.com.xuroot.common.pojo.PageEntity;
import cn.com.xuroot.common.pojo.ResponseResult;
import cn.com.xuroot.entity.UndeterminedUser;
import cn.com.xuroot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import cn.com.xuroot.entity.User;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "用户模块")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public ResponseResult<User> login(@RequestBody User user) {
        User user1 = userService.getUser(user);
        if (user1 == null)
            return ResponseResult.error(-1, "用户名或密码错误");
        return ResponseResult.success(user1);
    }

    @PostMapping("/enrollSchoolUser")
    @Operation(summary = "学校用户待定注册")
    public ResponseResult<Integer> enroll(@RequestBody UndeterminedUser undeterminedUser) {
        int i = userService.insertUndeterminedUser(undeterminedUser);
        return i == 1 ? ResponseResult.success(i) : ResponseResult.error(-1, "注册失败");
    }

    @PostMapping("/enroll")
    @Operation(summary = "用户注册")
    public ResponseResult<Integer> enroll(@RequestBody User user) {
        int i = userService.insertUser(user);
        return i == 1 ? ResponseResult.success(i) : ResponseResult.error(-1, "注册失败");
    }

    @GetMapping("/getAllUserPage")
    @Operation(summary = "获取所有用户")
    public ResponseResult<PageEntity<User>> getAllUserPage(@Parameter(name = "realName", description = "真实名字，可通过模糊查询查找")
                                                           @RequestParam(required = false) String realName,
                                                           @Parameter(name = "role", description = "角色")
                                                           @RequestParam(required = false) String role,
                                                           @Parameter(name = "pageIndex", description = "页码") Integer pageIndex,
                                                           @Parameter(name = "pageSize", description = "每页数量") Integer pageSize
    ) {
        PageEntity<User> allUserPage = userService.getAllUserPage(realName, role, pageIndex, pageSize);
        if (allUserPage == null) {
            return ResponseResult.error(-1, "获取用户失败");
        } else {
            return ResponseResult.success(allUserPage);
        }
    }

    @DeleteMapping("/deleteUser")
    @Operation(summary = "删除用户")
    public ResponseResult<Integer> deleteUser(@Parameter(name = "userId", description = "用户id")
                                              @RequestParam Integer userId) {
        Integer i = userService.deleteUser(userId);
        return i == 1 ? ResponseResult.success(i) : ResponseResult.error(-1, "该用户已删除");
    }

    @GetMapping("/getUserByClassId")
    @Operation(summary = "获取班级用户")
    public ResponseResult<PageEntity<User>> getUserByClassId(@Parameter(name = "teacherId", description = "教师id")
                                                             @RequestParam Integer teacherId,
                                                             @Parameter(name = "realName", description = "真实名字，可通过模糊查询查找")
                                                             @RequestParam(required = false) String realName,
                                                             @Parameter(name = "role", description = "角色")
                                                             @RequestParam(required = false) String role,
                                                             @Parameter(name = "pageIndex", description = "页码")
                                                             @RequestParam(required = false) Integer pageIndex,
                                                             @Parameter(name = "pageSize", description = "每页数量")
                                                             @RequestParam(required = false) Integer pageSize
    ) {
        PageEntity<User> userPageEntity = userService.getUserByClassId(teacherId, realName, role, pageIndex, pageSize);
        if (userPageEntity == null) {
            return ResponseResult.error(-1, "获取用户失败");
        } else {
            return ResponseResult.success(userPageEntity);
        }
    }

    @PutMapping("/updatePassword")
    @Operation(summary = "修改密码")
    public ResponseResult<Integer> updatePassword(@RequestParam String password, @RequestParam Integer userId) {
        return userService.updatePassword(password, userId) == 1 ? ResponseResult.success(1) : ResponseResult.error(-1, "修改密码失败");
    }

    @GetMapping("/getUserIdByClassId")
    @Operation(summary = "获取班级学生id")
    public ResponseResult<List<Integer>> getUserIdByClassId(@Parameter(name = "classId", description = "班级id")
                                                            @RequestParam Integer classId) {
        List<Integer> userIdByClassId = userService.getUserIdByClassId(classId);
        if (userIdByClassId == null) {
            return ResponseResult.error(-1, "获取用户id失败");
        } else {
            return ResponseResult.success(userIdByClassId);
        }
    }
    @GetMapping("/getRealNameByUserId")
    @Operation(summary = "获取用户真实名字")
    public ResponseResult<String> getRealNameByUserId(@Parameter(name = "userId", description = "用户id") @RequestParam Integer userId) {
        String realName = userService.getRealNameByUserId(userId);
        return realName == null  ? ResponseResult.error(-1, "获取用户真实名字失败") : ResponseResult.success(realName);
    }
}
