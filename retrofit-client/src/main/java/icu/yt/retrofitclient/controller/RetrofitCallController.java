package icu.yt.retrofitclient.controller;

import icu.yt.retrofitclient.model.Result;
import icu.yt.retrofitclient.retrofit.RetrofitCilent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yt
 * @date 2022/2/15 14:17
 * 功能说明
 */
@RestController
public class RetrofitCallController {

    @Autowired
    RetrofitCilent retrofitCilent;

    @GetMapping("/remote/get")
    public Result remoteGet () {
        return retrofitCilent.apiGet();
    }

    @PostMapping("/remote/post")
    public Result remoteGet (String text) {
        return retrofitCilent.apiPost(text);
    }

}
