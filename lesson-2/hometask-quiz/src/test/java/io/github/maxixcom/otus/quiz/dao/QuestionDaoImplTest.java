package io.github.maxixcom.otus.quiz.dao;

import io.github.maxixcom.otus.quiz.domain.Question;
import io.github.maxixcom.otus.quiz.domain.QuestionChoice;
import io.github.maxixcom.otus.quiz.domain.QuestionGeneral;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class QuestionDaoImplTest {

    @Mock
    private QuestionLoader questionLoader;

    @Test
    void findAll() {
        List<Question> questions = List.of(
                new QuestionGeneral("What color do you like?"),
                new QuestionChoice("What num called as two?", List.of("1", "2", "3"))
        );
        Mockito.when(questionLoader.load())
                .thenReturn(questions);

        QuestionDaoImpl questionDao = new QuestionDaoImpl(questionLoader);

        var allQuestions = questionDao.findAll();

        assertThat(allQuestions).size().isEqualTo(2);

        var firstQuestion = allQuestions.get(0);
        assertThat(firstQuestion).isInstanceOf(QuestionGeneral.class);

        var secondQuestion = allQuestions.get(1);
        assertThat(secondQuestion).isInstanceOf(QuestionChoice.class);
    }
}