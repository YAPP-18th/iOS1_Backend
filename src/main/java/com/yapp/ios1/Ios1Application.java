package com.yapp.ios1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Ios1Application {

    public static void main(String[] args) {
        SpringApplication.run(Ios1Application.class, args);
    }
}
