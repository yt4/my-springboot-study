package icu.yt.remoteprovider.controller;

import icu.yt.remoteprovider.model.Result;
import icu.yt.remoteprovider.model.User;
import icu.yt.remoteprovider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yt
 * @date 2022/2/22 10:36
 * 功能说明
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public Result<List<User>> selectUserList() {
        return Result.success(userService.selectUserList());
    }

    @PostMapping("/add")
    public Result<User> addUser(User user) {
        return Result.success(userService.addUser(user));
    }
}
