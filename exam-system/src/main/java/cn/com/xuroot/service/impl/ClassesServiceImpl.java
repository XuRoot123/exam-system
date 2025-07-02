package cn.com.xuroot.service.impl;

import cn.com.xuroot.dao.ClassesDao;
import cn.com.xuroot.entity.Classes;
import cn.com.xuroot.service.ClassesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesServiceImpl implements ClassesService{

    private final ClassesDao classesDao;

    public ClassesServiceImpl(ClassesDao classesDao) {
        this.classesDao = classesDao;
    }

    @Override
    public List<Classes> getAllClasses() {
        return classesDao.getAllClasses();
    }
}
