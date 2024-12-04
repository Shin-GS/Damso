package com.damso.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.damso.user", "com.damso.repository", "com.damso.core"})
@EnableJpaRepositories(basePackages = "com.damso.repository")
@EntityScan(basePackages = "com.damso.repository")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
