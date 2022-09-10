package io.github.maxixcom.otus.quiz.domain;

public abstract class Question {
    private final String content;
    private final Answer correctAnswer;

    protected Question(String content, Answer correctAnswer) {
        this.content = content;
        this.correctAnswer = correctAnswer;
    }

    public String getContent() {
        return this.content;
    }

    public Answer getCorrectAnswer() {
        return correctAnswer;
    }
}
