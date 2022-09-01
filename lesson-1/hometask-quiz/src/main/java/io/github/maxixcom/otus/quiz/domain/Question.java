package io.github.maxixcom.otus.quiz.domain;

public abstract class Question {
    private final String content;

    protected Question(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }
}
