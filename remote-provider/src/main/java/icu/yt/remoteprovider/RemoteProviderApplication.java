package icu.yt.remoteprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "icu.yt.remoteprovider.dao")
public class RemoteProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RemoteProviderApplication.class, args);
    }

}
