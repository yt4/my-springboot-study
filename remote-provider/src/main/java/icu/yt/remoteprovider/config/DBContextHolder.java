package icu.yt.remoteprovider.config;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yt
 * @date 2022/2/22 10:00
 * 功能说明
 * 通过ThreadLocal将数据源设置到每个线程上下文中，设置体现在AOP切面中国
 */
@Slf4j
public class DBContextHolder {

    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    private static void set(DBTypeEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        return contextHolder.get();
    }

    public static void switchMaster() {
        set(DBTypeEnum.MASTER);
        log.info("切换为master数据源");
    }

    public static void switchSlave() {
        // 当配置了多个slave源时，应设计一种选举方法来设置每次使用的slave源
        // 最简单的就是轮询
        set(DBTypeEnum.SLAVE);
        log.info("切换为slave数据源");
    }
}
