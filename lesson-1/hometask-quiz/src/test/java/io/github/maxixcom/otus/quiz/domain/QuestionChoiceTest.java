package io.github.maxixcom.otus.quiz.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class QuestionChoiceTest {
    @Test
    void shouldCreateCorrectQuestionChoice() {
        var question = new QuestionChoice("What number is bigger then others?", List.of("5", "3", "1"));

        assertAll("question",
                () -> assertThat(question.getContent())
                        .isEqualTo("What number is bigger then others?"),
                () -> assertThat(question.getOptions())
                        .hasSize(3)
                        .hasSameElementsAs(List.of("5", "3", "1"))
        );
    }
}