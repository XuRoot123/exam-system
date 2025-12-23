package cn.com.xuroot.service.impl;

import cn.com.xuroot.dao.ClassesDao;
import cn.com.xuroot.entity.Classes;
import cn.com.xuroot.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesServiceImpl implements ClassesService{

    @Autowired
    private ClassesDao classesDao;

    @Override
    public List<Classes> getAllClasses() {
        return classesDao.getAllClasses();
    }


}
