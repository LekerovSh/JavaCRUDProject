package com.iql.javaCRUD;

import com.iql.javaCRUD.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {UserRepository.class})
@EnableScheduling
public class JavatestProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavatestProjectApplication.class, args);
    }

}
