package io.github.maxixcom.otus.quiz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Locale;

@ConfigurationProperties(prefix = "quiz")
@ConstructorBinding
public class QuizConfigProperties {
    private final String path;
    private final Float correctAnswerRatio;
    private final Locale locale;

    public QuizConfigProperties(String path, Float correctAnswerRatio, Locale locale) {
        this.path = path;
        this.correctAnswerRatio = correctAnswerRatio;
        this.locale = locale;
    }

    public String getPath() {
        return path;
    }

    public Float getCorrectAnswerRatio() {
        return correctAnswerRatio;
    }

    public Locale getLocale() {
        return locale;
    }
}
