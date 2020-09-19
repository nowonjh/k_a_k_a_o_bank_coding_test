package com.kakao.codingtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication (scanBasePackages = {"com.kakao"})
@EnableScheduling
public class BackupMainApplication {
	public static void main(String[] args) {
		final SpringApplication application = new SpringApplication(BackupMainApplication.class);
		application.addListeners(new ApplicationPidFileWriter());
		application.run(args);
	}
}
