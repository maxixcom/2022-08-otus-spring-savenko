package io.github.maxixcom.otus.quiz;

import io.github.maxixcom.otus.quiz.config.AppConfig;
import io.github.maxixcom.otus.quiz.service.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        applicationContext.getBean("applicationRunnerImpl", ApplicationRunner.class)
                .run();
    }
}