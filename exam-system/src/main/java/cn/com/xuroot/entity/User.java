package cn.com.xuroot.entity;

import lombok.Data;

@Data
public class User {
    private Integer user_id;
    private String userName;
    private String password;
    private String real_name;
    private String email;
    private String phone;
    private String role;
    private Integer class_id;
    private Integer sex;
    private String created_at;
}
