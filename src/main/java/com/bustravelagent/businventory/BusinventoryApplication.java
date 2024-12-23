package com.bustravelagent.businventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BusinventoryApplication {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(BusinventoryApplication.class);

        // Set the active profile programmatically
        application.setAdditionalProfiles("dev");

        application.run(args);
    }

}
