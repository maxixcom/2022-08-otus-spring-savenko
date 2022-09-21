package io.github.maxixcom.otus.quiz.service;

import org.springframework.context.NoSuchMessageException;

public interface TranslationService {
    String translate(String code, Object... args) throws NoSuchMessageException;
}
