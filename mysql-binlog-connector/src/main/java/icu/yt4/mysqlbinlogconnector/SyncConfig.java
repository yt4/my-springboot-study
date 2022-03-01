package icu.yt4.mysqlbinlogconnector;

import lombok.Data;

/**
 * @author yt
 * @date 2022/3/1 11:14
 * 功能说明
 */
@Data
public class SyncConfig {
    /**
     * mysql的host配置
     */
    String host;
    /**
     * mysql的port配置
     */
    Integer port;
    /**
     * mysql的userName配置
     */
    String userName;
    /**
     * mysql的password配置
     */
    String password;
}
