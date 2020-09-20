package com.kakao.codingtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication (scanBasePackages = {"com.kakao"})
@EnableScheduling
public class MainApplication {
    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(MainApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }
}
