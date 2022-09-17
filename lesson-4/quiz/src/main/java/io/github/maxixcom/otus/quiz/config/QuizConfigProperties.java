package io.github.maxixcom.otus.quiz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "quiz")
@ConstructorBinding
public class QuizConfigProperties {
    private final String file;
    private final Float correctAnswerRatio;

    public QuizConfigProperties(String file, Float correctAnswerRatio) {
        this.file = file;
        this.correctAnswerRatio = correctAnswerRatio;
    }

    public String getFile() {
        return file;
    }

    public Float getCorrectAnswerRatio() {
        return correctAnswerRatio;
    }
}
