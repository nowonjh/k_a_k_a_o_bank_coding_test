package com.kakao.codingtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication (scanBasePackages = {"com.kakao"})
@EnableScheduling
public class BackupApplication {

	public static void main(String[] args) {
		final SpringApplication application = new SpringApplication(BackupApplication.class);
		application.addListeners(new ApplicationPidFileWriter());
		application.run(args);
	}
}
