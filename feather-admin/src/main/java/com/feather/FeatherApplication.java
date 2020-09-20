package com.feather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.Ordered;

/**
 * 启动程序
 * 
 * @author feather
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableCaching(order = Ordered.HIGHEST_PRECEDENCE)
public class FeatherApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(FeatherApplication.class, args);
        System.out.println("\n启动成功\n\n");
    }
}