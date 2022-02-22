package icu.yt.remoteprovider.service.impl;

import icu.yt.remoteprovider.dao.UserDao;
import icu.yt.remoteprovider.model.User;
import icu.yt.remoteprovider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yt
 * @date 2022/2/22 10:39
 * 功能说明
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public List<User> selectUserList() {
        return userDao.selectAll();
    }

    @Override
    public User addUser(User user) {
        user.setCreateTime(new Date());
        userDao.insert(user);
        return user;
    }
}
