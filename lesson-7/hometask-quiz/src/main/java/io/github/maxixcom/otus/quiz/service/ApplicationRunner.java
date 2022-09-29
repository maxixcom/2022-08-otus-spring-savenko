package io.github.maxixcom.otus.quiz.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class ApplicationRunner implements CommandLineRunner {
    private final QuizRunner quizRunner;

    public ApplicationRunner(QuizRunner quizRunner) {
        this.quizRunner = quizRunner;
    }

    @Override
    public void run(String... args) throws Exception {
        quizRunner.run();
    }
}
