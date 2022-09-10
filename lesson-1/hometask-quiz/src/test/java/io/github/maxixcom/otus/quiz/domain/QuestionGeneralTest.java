package io.github.maxixcom.otus.quiz.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QuestionGeneralTest {
    @Test
    void shouldCreateCorrectQuestionGeneral() {
        var question = new QuestionGeneral("What is the first color of a rainbow?");

        assertThat(question.getContent())
                .isEqualTo("What is the first color of a rainbow?");
    }
}