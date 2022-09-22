package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.config.provider.LocaleProvider;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class TranslationServiceImpl implements TranslationService {
    private final MessageSource messageSource;
    private final Locale locale;

    public TranslationServiceImpl(MessageSource messageSource, LocaleProvider localeProvider) {
        this.messageSource = messageSource;
        this.locale = localeProvider.getLocale();
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
