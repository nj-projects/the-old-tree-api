package me.nolanjames.theoldtreeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TheOldTreeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheOldTreeApiApplication.class, args);
    }

}
