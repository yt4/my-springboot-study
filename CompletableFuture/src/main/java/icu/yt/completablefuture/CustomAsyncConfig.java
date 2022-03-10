package icu.yt.completablefuture;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yt
 * @date 2022/3/10 13:02
 * 功能说明
 */
@Configuration
@EnableAsync
@Slf4j
public class CustomAsyncConfig {


    @Bean
    public Executor customExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(5);
        //配置最大线程数
        executor.setMaxPoolSize(10);
        //配置队列大小
        executor.setQueueCapacity(10);
        //线程空闲等待时间
        executor.setKeepAliveSeconds(30);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("custom-thread-");
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        //执行初始化
        executor.initialize();
        log.info("自定义线程池[query-similarity]初始化成功,核心线程数={},最大线程数={},阻塞队列容量={}", 10, 20, 50);
        return executor;
    }
}
