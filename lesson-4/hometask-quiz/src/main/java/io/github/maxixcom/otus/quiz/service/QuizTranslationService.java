package io.github.maxixcom.otus.quiz.service;

import org.springframework.context.NoSuchMessageException;

public interface QuizTranslationService {
    String translate(String code, Object... args) throws NoSuchMessageException;

    default String translate(String code) throws NoSuchMessageException {
        return this.translate(code, new Object[]{});
    }
}
