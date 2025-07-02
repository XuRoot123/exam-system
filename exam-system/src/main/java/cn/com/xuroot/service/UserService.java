package cn.com.xuroot.service;

import cn.com.xuroot.common.pojo.PageEntity;
import cn.com.xuroot.entity.UndeterminedUser;
import cn.com.xuroot.entity.User;

import java.util.List;

public interface UserService {
    User getUser(User user);

    int insertUndeterminedUser(UndeterminedUser user);

    int insertUser(User user);

    PageEntity<User> getAllUserPage(String real_name, String role,
                                    Integer pageIndex,
                                    Integer pageSize);

    Integer deleteUser(Integer user_id);

    PageEntity<User> getUserByClassId(Integer user_id,
                          String real_name,
                          String role,
                          Integer pageIndex,
                          Integer pageSize);
}
