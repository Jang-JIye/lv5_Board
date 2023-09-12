package com.sparta.lv5_board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Lv5BoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lv5BoardApplication.class, args);
    }

}
