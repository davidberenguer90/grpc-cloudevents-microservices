package dev.dberenguer.grpccloudevents.checkout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CheckoutServiceApplication {
    public static void main(final String[] args)  {
        SpringApplication.run(CheckoutServiceApplication.class, args);
    }
}

