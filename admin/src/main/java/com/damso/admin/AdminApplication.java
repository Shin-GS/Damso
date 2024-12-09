package com.damso.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.damso.admin", "com.damso.domain", "com.damso.core"})
@EnableJpaRepositories(basePackages = "com.damso.domain")
@EntityScan(basePackages = "com.damso.domain")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
