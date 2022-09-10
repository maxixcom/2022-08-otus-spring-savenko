package io.github.maxixcom.otus.quiz.domain;

public class QuizQuestion {
    private final Question question;
    private boolean isAnswered = false;
    private boolean isAnsweredCorrectly = false;

    public QuizQuestion(Question question) {
        this.question = question;
    }

    public void giveAnswer(Answer answer) {
        if (this.isAnswered) {
            return;
        }
        this.isAnswered = true;
        this.isAnsweredCorrectly = question.getCorrectAnswer().equals(answer);
    }

    public Question getQuestion() {
        return question;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public boolean isAnsweredCorrectly() {
        return isAnsweredCorrectly;
    }
}
