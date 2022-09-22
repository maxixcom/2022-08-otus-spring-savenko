package io.github.maxixcom.otus.quiz.config;

import io.github.maxixcom.otus.quiz.config.provider.LocaleProvider;
import io.github.maxixcom.otus.quiz.config.provider.QuestionFileProvider;
import io.github.maxixcom.otus.quiz.config.provider.RatioProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Locale;

@ConfigurationProperties(prefix = "quiz")
@ConstructorBinding
public class QuizConfigProperties implements QuestionFileProvider, LocaleProvider, RatioProvider {
    private final String path;
    private final Float correctAnswerRatio;
    private final Locale locale;

    public QuizConfigProperties(String path, Float correctAnswerRatio, Locale locale) {
        this.path = path;
        this.correctAnswerRatio = correctAnswerRatio;
        this.locale = locale;
    }

    @Override
    public Float getRatio() {
        return correctAnswerRatio;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public String getFilePath() {
        return String.format(
                "%s/questions_%s.csv",
                path,
                locale.getLanguage()
        );
    }
}
