package cn.com.xuroot.dao;

import cn.com.xuroot.entity.UndeterminedUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UndeterminedUserDao {

    List<UndeterminedUser> getAllUndeterminedUserPage(@Param("real_name") String real_name,
                                                      @Param("role") String role,
                                                      @Param("pageIndex") Integer pageIndex,
                                                      @Param("pageSize") Integer pageSize,
                                                      @Param("level") String level,
                                                      @Param("class_id") Integer class_id
    );

    Integer getAllUndeterminedUserCount(@Param("real_name") String real_name,
                                        @Param("role") String role, @Param("level") String level, @Param("class_id") Integer class_id);

    @Delete("delete from undetermined_users where user_id = #{user_id}")
    Integer deleteUndeterminedUser(@Param("user_id") Integer user_id);

}
