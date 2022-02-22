package icu.yt.remoteprovider.service;

import icu.yt.remoteprovider.model.Result;
import icu.yt.remoteprovider.model.User;

import java.util.List;

/**
 * @author yt
 * @date 2022/2/22 10:39
 * 功能说明
 */
public interface UserService {

    List<User> selectUserList();

    User addUser(User user);
}
