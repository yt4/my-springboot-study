package icu.yt.remoteprovider.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

/**
 * @author yt
 * @date 2022/2/22 9:56
 * 功能说明
 * 在Spring 2.0.1中引入了AbstractRoutingDataSource, 该类充当了DataSource的路由中介, 能有在运行时, 根据某种key值来动态切换到真正的DataSource上。
 */
@Slf4j
public class MyRoutingDataSource extends AbstractRoutingDataSource {


    /**
     * 从ThreadLocal里拿到数据源枚举作为key从路由数据源初始化的map中获取到实际使用的数据源
     */
    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.get();
    }
}
