package io.github.maxixcom.otus.quiz;

import io.github.maxixcom.otus.quiz.service.ApplicationRunner;
import io.github.maxixcom.otus.quiz.service.InputOutputService;
import io.github.maxixcom.otus.quiz.service.console.ConsoleInputOutputService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class Main {
    @Bean
    InputOutputService outputService() {
        return new ConsoleInputOutputService(System.out, System.in);
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Main.class);
        applicationContext.getBean("consoleRunner", ApplicationRunner.class)
                .run();
    }
}