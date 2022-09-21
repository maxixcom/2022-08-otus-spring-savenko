package io.github.maxixcom.otus.quiz.dao.csv;

import io.github.maxixcom.otus.quiz.dao.QuestionLoader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

class QuestionCsvResourceLoaderTest {
    @Test
    void shouldLoadOnlyCorrectLines() {
        QuestionLoader questionLoader = new QuestionCsvResourceLoader("", new Locale("en", "US"));
        var questions = questionLoader.load();

        Assertions.assertThat(questions).size().isEqualTo(2);
    }
}