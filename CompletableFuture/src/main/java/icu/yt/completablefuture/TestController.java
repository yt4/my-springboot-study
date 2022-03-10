package icu.yt.completablefuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @author yt
 * @date 2022/3/10 13:20
 * 功能说明
 */
@RestController
public class TestController {

    @Autowired
    DemoService demoService;

    @GetMapping("test")
    public void test() throws ExecutionException, InterruptedException {
        demoService.scheduledTask();
    }

}
