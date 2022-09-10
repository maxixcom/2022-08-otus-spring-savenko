package io.github.maxixcom.otus.quiz.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuizQuestionTest {
    private QuizQuestion quizQuestion;

    @BeforeEach
    void setUp() {
        Question question = new QuestionGeneral("Some Question?", new Answer("Some Answer"));
        this.quizQuestion = new QuizQuestion(question);
    }

    @Test
    void unansweredToQuestion() {
        Assertions.assertThat(quizQuestion.isAnswered()).isFalse();
        Assertions.assertThat(quizQuestion.isAnsweredCorrectly()).isFalse();
    }

    @Test
    void correctAnswerToQuestion() {
        quizQuestion.giveAnswer(new Answer("Some Answer"));

        Assertions.assertThat(quizQuestion.isAnswered()).isTrue();
        Assertions.assertThat(quizQuestion.isAnsweredCorrectly()).isTrue();
    }

    @Test
    void incorrectAnswerToQuestion() {
        quizQuestion.giveAnswer(new Answer("Incorrect answer"));

        Assertions.assertThat(quizQuestion.isAnswered()).isTrue();
        Assertions.assertThat(quizQuestion.isAnsweredCorrectly()).isFalse();
    }

    @Test
    void giveAnotherAnswerToQuestion() {
        quizQuestion.giveAnswer(new Answer("Incorrect answer"));
        quizQuestion.giveAnswer(new Answer("Some Answer"));

        Assertions.assertThat(quizQuestion.isAnsweredCorrectly()).isFalse();
    }
}