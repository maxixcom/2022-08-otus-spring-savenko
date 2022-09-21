package io.github.maxixcom.otus.quiz.service;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

public class TranslationServiceImpl implements TranslationService {
    private final MessageSource messageSource;
    private final Locale locale;

    public TranslationServiceImpl(MessageSource messageSource, Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public String translate(String code, Object[] args) throws NoSuchMessageException {
        try {
            return this.messageSource.getMessage(code, args, locale);
        } catch (NoSuchMessageException e) {
            return code;
        }
    }

}
