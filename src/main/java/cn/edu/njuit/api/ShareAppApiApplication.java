package cn.edu.njuit.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author DingYihang
 */
@SpringBootApplication
@MapperScan(basePackages = {"cn.edu.njuit.api.mapper"})
public class ShareAppApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShareAppApiApplication.class, args);
    }

}
