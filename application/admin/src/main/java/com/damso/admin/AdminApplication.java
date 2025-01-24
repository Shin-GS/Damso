package com.damso.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.damso.admin",
        "com.damso.adminservice",
        "com.damso.common",
        "com.damso.storage",
        "com.damso.cache",
        "com.damso.core"
})
@EnableJpaRepositories(basePackages = {"com.damso.storage", "com.damso.cache"})
@EntityScan(basePackages = {"com.damso.storage", "com.damso.cache"})
@EnableJpaAuditing
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
