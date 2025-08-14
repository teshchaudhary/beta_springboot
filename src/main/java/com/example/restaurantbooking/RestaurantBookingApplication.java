// package com.example;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
// public class RestaurantBookingApplication {
//     public static void main(String[] args) {
//         SpringApplication.run(RestaurantBookingApplication.class, args);
//     }
// }


package com.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestaurantBookingApplication {
    public static void main(String[] args) {
        // Load .env
        Dotenv dotenv = Dotenv.configure()
                .directory(".")   // project root
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));

        SpringApplication.run(RestaurantBookingApplication.class, args);
    }
}
