package cn.com.xuroot.entity;

import lombok.Data;

@Data
public class UndeterminedUser {
    private Integer userId;
    private String userName;
    private String password;
    private String realName;
    private String email;
    private String phone;
    private String role;
    private Integer classId;
    private Integer sex;
    private String level;
    private String createdAt;
}
