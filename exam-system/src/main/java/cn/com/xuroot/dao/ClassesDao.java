package cn.com.xuroot.dao;

import cn.com.xuroot.entity.Classes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassesDao {

    @Select("select * from classes")
    List<Classes> getAllClasses();


    @Select("select * from classes where teacher_id = #{teacherId}")
    Classes getClassesByTeacherId(@Param("teacherId") Integer teacherId);

    @Select("select class_id from classes where teacher_id = #{teacherId}")
    int getAllClassIdByTeacherId(@Param("teacherId") Integer teacherId);
}
