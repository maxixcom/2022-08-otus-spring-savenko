package io.github.maxixcom.otus.quiz.dao.csv;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuestionCsvResourceLoaderTest {
    @Autowired
    private QuestionCsvResourceLoader questionCsvResourceLoader;

    @Test
    void shouldLoadOnlyCorrectLines() {
        var questions = questionCsvResourceLoader.load();

        Assertions.assertThat(questions).size().isEqualTo(2);
    }
}