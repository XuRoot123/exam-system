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

    @Insert("insert into undetermined_users(username, password, real_name, email, phone, role, class_id, sex,level) values(#{userName}, #{password}, #{realName}, #{email}, #{phone}, #{role}, #{classId}, #{sex},#{level})")
    int insertUndeterminedUser(UndeterminedUser user);

    @Insert("insert into users(username, password, real_name, email, phone, role, class_id, sex, created_at) values(#{userName}, #{password}, #{realName}, #{email}, #{phone}, #{role}, #{classId}, #{sex}, #{createdAt})")
    int insertUser(User user);


    List<User> getAllUserPage(@Param("realName") String realName,
                              @Param("role") String role,
                              @Param("pageIndex") Integer pageIndex,
                              @Param("pageSize") Integer pageSize);

    Integer getAllUserCount(@Param("realName") String realName,
                            @Param("role") String role);

    @Delete("delete from users where user_id = #{userId}")
    Integer deleteUser(@Param("userId") Integer userId);

    @Update("update classes set teacher_id = 17 where teacher_id = #{userId}")
    Integer updateClassTeacherIdByUserId(@Param("userId") Integer userId);

    List<User> getUserByClassId(@Param("classId") Integer classId, @Param("realName") String realName,
                                @Param("role") String role,
                                @Param("pageIndex") Integer pageIndex,
                                @Param("pageSize") Integer pageSize);

    Integer getUserByClassIdCount(@Param("classId") Integer classId, @Param("realName") String realName,
                                  @Param("role") String role);

    @Update("update users set password = #{password} where user_id = #{userId}")
    int updatePassword(String password , Integer userId);

    @Select("select user_id from users where class_id = #{classId} and role='student'")
    List<Integer> getUserIdByClassId(@Param("classId") Integer classId);

    @Select("select real_name from users where user_id = #{userId}")
    String getRealNameByUserId(Integer userId);
}