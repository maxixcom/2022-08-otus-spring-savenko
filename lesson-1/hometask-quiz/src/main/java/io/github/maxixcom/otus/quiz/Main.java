package io.github.maxixcom.otus.quiz;

import io.github.maxixcom.otus.quiz.service.QuizService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");

        QuizService quizService = applicationContext.getBean("quizService", QuizService.class);
        quizService.printAllQuestions();

        applicationContext.close();
    }
}