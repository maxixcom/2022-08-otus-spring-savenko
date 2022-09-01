package io.github.maxixcom.otus.quiz;

import io.github.maxixcom.otus.quiz.service.ConsoleQuizService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");

        ConsoleQuizService consoleQuizService = applicationContext.getBean("consoleQuizService", ConsoleQuizService.class);
        consoleQuizService.printAllQuestions();

        applicationContext.close();
    }
}