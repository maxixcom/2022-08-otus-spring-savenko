package io.github.maxixcom.otus.quiz.domain;

import java.util.List;

public class QuestionChoice extends Question {
    private final List<String> options;

    public QuestionChoice(String content, List<String> options) {
        super(content);
        this.options = options;
    }

    public List<String> getOptions() {
        return options;
    }
}
