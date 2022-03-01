package icu.yt4.mysqlbinlogconnector.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yt
 * @date 2022/3/1 13:06
 * 功能说明
 */
@ConfigurationProperties(prefix = "db")
@Data
@Component
public class DbConfig {

    private String host;
    private Integer port;
    private String username;
    private String password;
}
