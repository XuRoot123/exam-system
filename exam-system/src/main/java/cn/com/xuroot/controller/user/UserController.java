package cn.com.xuroot.controller.user;

import cn.com.xuroot.common.pojo.PageEntity;
import cn.com.xuroot.common.pojo.ResponseResult;
import cn.com.xuroot.entity.UndeterminedUser;
import cn.com.xuroot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import cn.com.xuroot.entity.User;
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
    public ResponseResult<PageEntity<User>> getAllUserPage(@Parameter(name = "real_name", description = "真实名字，可通过模糊查询查找")
                                                           @RequestParam(required = false) String real_name,
                                                           @Parameter(name = "role", description = "角色")
                                                           @RequestParam(required = false) String role,
                                                           @Parameter(name = "pageIndex", description = "页码") Integer pageIndex,
                                                           @Parameter(name = "pageSize", description = "每页数量") Integer pageSize
    ) {
        PageEntity<User> allUserPage = userService.getAllUserPage(real_name, role, pageIndex, pageSize);
        if (allUserPage == null) {
            return ResponseResult.error(-1, "获取用户失败");
        } else {
            return ResponseResult.success(allUserPage);
        }
    }

    @DeleteMapping("/deleteUser")
    @Operation(summary = "删除用户")
    public ResponseResult<Integer> deleteUser(@Parameter(name = "user_id", description = "用户id")
                                              @RequestParam Integer user_id) {
        Integer i = userService.deleteUser(user_id);
        return i == 1 ? ResponseResult.success(i) : ResponseResult.error(-1, "该用户已删除");
    }

    @GetMapping("/getUserByClassId")
    @Operation(summary = "获取班级用户")
    public ResponseResult<PageEntity<User>> getUserByClassId(@Parameter(name = "teacher_id", description = "教师id")
                                                             @RequestParam Integer teacher_id,
                                                             @Parameter(name = "real_name", description = "真实名字，可通过模糊查询查找")
                                                             @RequestParam(required = false) String real_name,
                                                             @Parameter(name = "role", description = "角色")
                                                             @RequestParam(required = false) String role,
                                                             @Parameter(name = "pageIndex", description = "页码")
                                                             @RequestParam(required = false) Integer pageIndex,
                                                             @Parameter(name = "pageSize", description = "每页数量")
                                                             @RequestParam(required = false) Integer pageSize
    ) {
        PageEntity<User> userPageEntity = userService.getUserByClassId(teacher_id, real_name, role, pageIndex, pageSize);
        if (userPageEntity == null) {
            return ResponseResult.error(-1, "获取用户失败");
        } else {
            return ResponseResult.success(userPageEntity);
        }
    }
}
