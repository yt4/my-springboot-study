package icu.yt.remoteprovider.controller;

import icu.yt.remoteprovider.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yt
 * @date 2022/2/15 13:29
 * 功能说明
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/get")
    public Result<String> getApi() {
        return Result.success("get api result");
    }

    @PostMapping("/post")
    public Result<String> postApi(String text) {
        return Result.success("post api reply to message:" + text);
    }

    @RequestMapping("/test")
    public String test() {
        return "ok";
    }
}
