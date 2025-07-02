package cn.com.xuroot.service.impl;

import cn.com.xuroot.common.pojo.PageEntity;
import cn.com.xuroot.dao.ClassesDao;
import cn.com.xuroot.dao.UserDao;
import cn.com.xuroot.entity.UndeterminedUser;
import cn.com.xuroot.entity.User;
import cn.com.xuroot.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ClassesDao classesDao;

    public UserServiceImpl(UserDao userDao,ClassesDao classesDao) {
        this.userDao = userDao;
        this.classesDao = classesDao;
    }

    @Override
    public User getUser(User user) {
        return userDao.getUser(user);
    }

    @Override
    public int insertUndeterminedUser(UndeterminedUser undeterminedUser) {
        return userDao.insertUndeterminedUser(undeterminedUser);
    }

    @Override
    public int insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public PageEntity<User> getAllUserPage(String real_name, String role,
                                           Integer pageIndex,
                                           Integer pageSize) {
        int total = userDao.getAllUserCount(real_name, role);
        if (total > 0) {
            PageEntity<User> userPageEntity = new PageEntity<>();
            List<User> list = userDao.getAllUserPage(real_name, role, pageIndex, pageSize);
            userPageEntity.setList(list);
            userPageEntity.setTotal(total);
            return userPageEntity;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Integer deleteUser(Integer user_id) {
        userDao.updateClassTeacherIdByUserId(user_id);
        return userDao.deleteUser(user_id);
    }

    @Override
    public PageEntity<User> getUserByClassId(Integer user_id,
                                             String real_name,
                                             String role,
                                             Integer pageIndex,
                                             Integer pageSize) {
        int class_id = classesDao.getClassesByTeacherId(user_id).getClass_id();
        int total = userDao.getUserByClassIdCount(class_id,real_name, role);
        if (total > 0) {
            PageEntity<User> userPageEntity = new PageEntity<>();
            List<User> list = userDao.getUserByClassId(class_id, real_name, role, pageIndex, pageSize);
            userPageEntity.setList(list);
            userPageEntity.setTotal(total);
            return userPageEntity;
        }
        return null;
    }

}
