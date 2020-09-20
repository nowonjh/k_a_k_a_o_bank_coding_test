/*
 * Licensed to the Yuganji Software Foundation (YSF) under only one
 * contributor license agreements. The YSF licenses this file
 * to you under the Yuganji License, Version Beta (the
 * "License"); you may not use this file except in compliance
 * with the License.  You can not obtain a copy of the License at anywhere
 *
 *     I know it won't happens but, if you want quote this source code.
 *     Please hire me
 *
 * limitations under the License.
 */
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
