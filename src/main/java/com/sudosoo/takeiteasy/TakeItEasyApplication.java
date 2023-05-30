package com.sudosoo.takeiteasy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@Configuration
@EnableJpaAuditing
public class TakeItEasyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeItEasyApplication.class, args);
    }

}
