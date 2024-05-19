package ch.unifr.softeng.todobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableTransactionManagement // To preserve ACID principles on the DB
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Declare a Spring Bean, which is an object that is managed by the Spring container.
    // The Spring Bean here is a WebMvcConfigurer that manages CORS (Cross-Origin Resource Sharing).
    // With this CORS configuration, every domain name can make HTTP requests to this server.
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**") // Allows CORS requests on all URLs of this server application
                        .allowedMethods("*") // Allows all HTTP methods (GET, POST, PUT, DELETE, etc.)
                        .allowedOrigins("*"); // Allows CORS requests from any origin (all domains)
            }
        };
    }
}

