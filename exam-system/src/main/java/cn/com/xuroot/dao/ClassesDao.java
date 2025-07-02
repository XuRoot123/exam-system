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


    @Select("select * from classes where teacher_id = #{teacher_id}")
    Classes getClassesByTeacherId(@Param("teacher_id") Integer teacher_id);
}
