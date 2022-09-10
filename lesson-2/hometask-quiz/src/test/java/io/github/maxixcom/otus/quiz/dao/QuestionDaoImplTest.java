package io.github.maxixcom.otus.quiz.dao;

import io.github.maxixcom.otus.quiz.domain.Answer;
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
                new QuestionGeneral(
                        "What is the first color of the Rainbow?",
                        new Answer("red")
                ),
                new QuestionChoice(
                        "What num called as two?",
                        new Answer("2"),
                        List.of(
                                new Answer("1"),
                                new Answer("2"),
                                new Answer("3")
                        )
                )
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