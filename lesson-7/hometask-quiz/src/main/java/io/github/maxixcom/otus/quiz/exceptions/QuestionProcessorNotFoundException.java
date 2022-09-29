package io.github.maxixcom.otus.quiz.exceptions;

public class QuestionProcessorNotFoundException extends RuntimeException {
    public QuestionProcessorNotFoundException(String message) {
        super(message);
    }
}
