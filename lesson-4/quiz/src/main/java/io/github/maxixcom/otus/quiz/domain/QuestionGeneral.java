package io.github.maxixcom.otus.quiz.domain;

public class QuestionGeneral extends Question {
    private final Answer correctAnswer;

    public QuestionGeneral(String content, Answer correctAnswer) {
        super(content);
        this.correctAnswer = correctAnswer;
    }

    public Answer getCorrectAnswer() {
        return correctAnswer;
    }
}
