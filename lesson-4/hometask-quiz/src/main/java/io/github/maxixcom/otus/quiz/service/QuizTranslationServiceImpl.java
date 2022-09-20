package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.config.QuizConfigProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class QuizTranslationServiceImpl implements QuizTranslationService {
    private final MessageSource messageSource;
    private final Locale locale;

    public QuizTranslationServiceImpl(MessageSource messageSource, QuizConfigProperties quizConfigProperties) {
        this.messageSource = messageSource;
        this.locale = quizConfigProperties.getLocale();
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
