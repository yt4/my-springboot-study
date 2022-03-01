package icu.yt4.mysqlbinlogconnector.config;

import icu.yt4.mysqlbinlogconnector.service.MysqlBinlogEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author yt
 * @date 2022/3/1 13:23
 * 功能说明
 */
@Slf4j
@Component
public class MysqlCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        MysqlBinlogEventListener.start();
        log.info("success");
    }
}
