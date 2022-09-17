package io.github.maxixcom.otus.quiz.domain;

import java.util.List;

public class QuestionChoice extends Question {
    private final List<Answer> options;
    private final Answer correctAnswer;

    public QuestionChoice(String content, Answer correctAnswer, List<Answer> options) {
        super(content);
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    public List<Answer> getOptions() {
        return options;
    }

    public Answer getCorrectAnswer() {
        return correctAnswer;
    }
}
