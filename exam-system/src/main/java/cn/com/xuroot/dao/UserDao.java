package cn.com.xuroot.dao;

import cn.com.xuroot.common.pojo.PageEntity;
import cn.com.xuroot.entity.UndeterminedUser;
import cn.com.xuroot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {
    @Select("select * from users where username = #{userName} and password = #{password}")
    User getUser(User user);

    @Insert("insert into undetermined_users(username, password, real_name, email, phone, role, class_id, sex,level) values(#{userName}, #{password}, #{real_name}, #{email}, #{phone}, #{role}, #{class_id}, #{sex},#{level})")
    int insertUndeterminedUser(UndeterminedUser user);

    @Insert("insert into users(username, password, real_name, email, phone, role, class_id, sex, created_at) values(#{userName}, #{password}, #{real_name}, #{email}, #{phone}, #{role}, #{class_id}, #{sex}, #{created_at})")
    int insertUser(User user);


    List<User> getAllUserPage(@Param("real_name") String real_name,
                              @Param("role") String role,
                              @Param("pageIndex") Integer pageIndex,
                              @Param("pageSize") Integer pageSize);

    Integer getAllUserCount(@Param("real_name") String real_name,
                            @Param("role") String role);

    @Delete("delete from users where user_id = #{user_id}")
    Integer deleteUser(@Param("user_id") Integer user_id);

    @Update("update classes set teacher_id = 17 where teacher_id = #{user_id}")
    Integer updateClassTeacherIdByUserId(@Param("user_id") Integer user_id);

    List<User> getUserByClassId(@Param("class_id") Integer class_id, @Param("real_name") String real_name,
                                @Param("role") String role,
                                @Param("pageIndex") Integer pageIndex,
                                @Param("pageSize") Integer pageSize);

    Integer getUserByClassIdCount(@Param("class_id") Integer class_id, @Param("real_name") String real_name,
                                  @Param("role") String role);
}