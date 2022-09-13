package io.github.maxixcom.otus.quiz.service.processor;

import io.github.maxixcom.otus.quiz.domain.Answer;
import io.github.maxixcom.otus.quiz.domain.Question;
import io.github.maxixcom.otus.quiz.domain.QuizQuestion;
import io.github.maxixcom.otus.quiz.service.InputOutputService;
import io.github.maxixcom.otus.quiz.service.QuestionProcessor;
import org.springframework.stereotype.Component;

@Component
public class QuestionGeneralProcessor implements QuestionProcessor {
    private final InputOutputService inputOutputService;

    public QuestionGeneralProcessor(InputOutputService inputOutputService) {
        this.inputOutputService = inputOutputService;
    }

    @Override
    public void processQuestion(QuizQuestion quizQuestion) {
        Question question = quizQuestion.getQuestion();

        inputOutputService.printlnString(question.getContent());
        inputOutputService.printNewLine();

        inputOutputService.printString("Type your answer: ");

        while (true) {
            String userInput = inputOutputService.readLine().trim();
            if (userInput.isBlank()) {
                continue;
            }

            quizQuestion.giveAnswer(new Answer(userInput));

            break;
        }
    }
}
