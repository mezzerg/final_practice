package com.example.final_lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * FinalApplication is the main entry point for the Investment/Customer Management application.
 *
 * <p>This class bootstraps the Spring Boot framework by:
 * <ul>
 *   <li>Enabling auto-configuration via {@link SpringBootApplication}</li>
 *   <li>Scanning for components (controllers, services, repositories) in the package</li>
 *   <li>Starting the embedded server (Tomcat by default) on the configured port</li>
 * </ul>
 *
 * <p>When run, it initializes the Spring context and launches the application.
 */
@SpringBootApplication
public class FinalApplication {

    /**
     * Main method that starts the Spring Boot application.
     *
     * @param args command-line arguments (optional)
     */
    public static void main(String[] args) {
        SpringApplication.run(FinalApplication.class, args);
    }
}