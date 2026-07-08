package org.example.plandevelop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PlanDevelopApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanDevelopApplication.class, args);
    }

}
