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
    public PageEntity<User> getAllUserPage(String realName, String role,
                                           Integer pageIndex,
                                           Integer pageSize) {
        int total = userDao.getAllUserCount(realName, role);
        if (total > 0) {
            PageEntity<User> userPageEntity = new PageEntity<>();
            List<User> list = userDao.getAllUserPage(realName, role, pageIndex, pageSize);
            userPageEntity.setList(list);
            userPageEntity.setTotal(total);
            return userPageEntity;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Integer deleteUser(Integer userId) {
        userDao.updateClassTeacherIdByUserId(userId);
        return userDao.deleteUser(userId);
    }

    @Override
    public PageEntity<User> getUserByClassId(Integer userId,
                                             String realName,
                                             String role,
                                             Integer pageIndex,
                                             Integer pageSize) {
        int classId = classesDao.getClassesByTeacherId(userId).getClassId();
        int total = userDao.getUserByClassIdCount(classId,realName, role);
        if (total > 0) {
            PageEntity<User> userPageEntity = new PageEntity<>();
            List<User> list = userDao.getUserByClassId(classId, realName, role, pageIndex, pageSize);
            userPageEntity.setList(list);
            userPageEntity.setTotal(total);
            return userPageEntity;
        }
        return null;
    }

    @Override
    public int updatePassword(String password , Integer user_id) {
        return userDao.updatePassword(password , user_id);
    }

    @Override
    public List<Integer> getUserIdByClassId(Integer classId) {
        return userDao.getUserIdByClassId(classId);
    }

    @Override
    public String getRealNameByUserId(Integer userId) {
        return userDao.getRealNameByUserId(userId);
    }

}
