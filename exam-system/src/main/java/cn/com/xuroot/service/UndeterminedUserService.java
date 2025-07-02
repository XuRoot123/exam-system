package cn.com.xuroot.service;

import cn.com.xuroot.common.pojo.PageEntity;
import cn.com.xuroot.entity.UndeterminedUser;

public interface UndeterminedUserService {

    PageEntity<UndeterminedUser> getAllUndeterminedUserPage(String real_name, String role,
                                                            Integer pageIndex,
                                                            Integer pageSize,String level,Integer user_id);
    Integer deleteUndeterminedUser(Integer user_id);

}
