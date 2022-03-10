package icu.yt.completablefuture;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author yt
 * @date 2022/3/10 13:05
 * 功能说明
 */
@Service
@EnableScheduling
@Slf4j
public class DemoService {

    // 一分钟一次
    //@Scheduled(cron = "0 */1 * * * ?")
    public void scheduledTask() throws ExecutionException, InterruptedException {
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int[] ints = new int[10];
            for (int j = 0; j < 10; j++) {
                ints[j] = RandomUtil.randomInt(0, 20);
            }
            list.add(ints);
        }

        CompletableFuture<Result>[] result = new CompletableFuture[list.size()];

        list.forEach(ints -> {
            CompletableFuture<Result> future = findMaxIndex(ints);
            // 获取返回结果
            result[list.indexOf(ints)] = future;
        });

        //join() 的作用：让“主线程”等待“子线程”结束之后才能继续运行
        CompletableFuture.allOf(result).join();
        for (CompletableFuture<Result> completableFuture : result) {
            boolean done = completableFuture.isDone();
            if(done) {
                Result r = completableFuture.get();
                log.info("result:{}", r);
            }
        }
    }

    // 从list中找出最大的数字的下标返回
    @Async("customExecutor")
    public CompletableFuture<Result> findMaxIndex(int[] array) {
        int max = ArrayUtil.max(array);
        int i = ArrayUtil.indexOf(array, max);
        log.info("数组:{} 中的最大值为:{}, 下标为:{}", array, max, i);
        Result result = new Result();
        result.setMax(max);
        result.setIndex(i);
        return CompletableFuture.completedFuture(result);
    }

    @Data
    public static class Result {
        private Integer max;
        private Integer index;
    }

}
