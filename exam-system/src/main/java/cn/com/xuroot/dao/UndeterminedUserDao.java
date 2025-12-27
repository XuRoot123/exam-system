package cn.com.xuroot.dao;

import cn.com.xuroot.entity.UndeterminedUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UndeterminedUserDao {

    List<UndeterminedUser> getAllUndeterminedUserPage(@Param("realName") String realName,
                                                      @Param("role") String role,
                                                      @Param("pageIndex") Integer pageIndex,
                                                      @Param("pageSize") Integer pageSize,
                                                      @Param("level") String level,
                                                      @Param("classId") Integer classId
    );

    Integer getAllUndeterminedUserCount(@Param("realName") String realName,
                                        @Param("role") String role, @Param("level") String level, @Param("classId") Integer classId);

    @Delete("delete from undetermined_users where user_id = #{userId}")
    Integer deleteUndeterminedUser(@Param("userId") Integer userId);

}
