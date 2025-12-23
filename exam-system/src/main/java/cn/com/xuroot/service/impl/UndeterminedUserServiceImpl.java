package cn.com.xuroot.service.impl;

import cn.com.xuroot.common.pojo.PageEntity;
import cn.com.xuroot.dao.ClassesDao;
import cn.com.xuroot.dao.UndeterminedUserDao;
import cn.com.xuroot.entity.UndeterminedUser;
import cn.com.xuroot.entity.User;
import cn.com.xuroot.service.UndeterminedUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UndeterminedUserServiceImpl implements UndeterminedUserService {
    private final UndeterminedUserDao undeterminedUserDao;
    private final ClassesDao classesDao;
    public UndeterminedUserServiceImpl(ClassesDao classesDao, UndeterminedUserDao undeterminedUserDao){
        this.undeterminedUserDao = undeterminedUserDao;
        this.classesDao = classesDao;
    }

    @Override
    public PageEntity<UndeterminedUser> getAllUndeterminedUserPage(String real_name, String role,
                                                                   Integer pageIndex,
                                                                   Integer pageSize,
                                                                    String level,
                                                                   Integer user_id
                                                                   ) {
        int class_id = classesDao.getClassesByTeacherId(user_id).getClassId();
        int total = undeterminedUserDao.getAllUndeterminedUserCount(real_name, role, level, class_id);
        if (total > 0) {
            PageEntity<UndeterminedUser> undeterminedUserPageEntity = new PageEntity<>();
            List<UndeterminedUser> list = undeterminedUserDao.getAllUndeterminedUserPage(real_name, role, pageIndex, pageSize, level, class_id);
            undeterminedUserPageEntity.setList(list);
            undeterminedUserPageEntity.setTotal(total);
            return undeterminedUserPageEntity;
        }
        return null;
    }

    @Override
    public Integer deleteUndeterminedUser(Integer user_id) {
        return undeterminedUserDao.deleteUndeterminedUser(user_id);
    }


}
