package com.tinychating.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = {"com.tinychating"})
@MapperScan({"com.tinychating.common.**.mapper"})
@ServletComponentScan
public class TinyChatingCustomApplication {

    public static void main(String[] args) {
        SpringApplication.run(TinyChatingCustomApplication.class,args);
    }

}