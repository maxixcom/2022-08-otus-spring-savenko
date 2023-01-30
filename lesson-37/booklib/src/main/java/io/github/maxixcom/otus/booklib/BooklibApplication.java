package io.github.maxixcom.otus.booklib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
public class BooklibApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(BooklibApplication.class, args);
    }

}
