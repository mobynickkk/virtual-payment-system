package ru.mnk.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"ru.mnk"})
@EnableJpaRepositories("ru.mnk.domain.repository")
@EntityScan("ru.mnk.domain.entity")
public class ApplicationEntryPoint {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationEntryPoint.class, args);
    }
}
