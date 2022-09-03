package io.github.maxixcom.otus.quiz.dao.csv;

import io.github.maxixcom.otus.quiz.dao.QuestionLoader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionCsvResourceLoaderTest {

    @Test
    void shouldLoadOnlyCorrectLines() {
        QuestionLoader questionLoader = new QuestionCsvResourceLoader();
        var questions = questionLoader.load();

        Assertions.assertThat(questions).size().isEqualTo(3);
    }
}