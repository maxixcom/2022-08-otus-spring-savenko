package io.github.maxixcom.otus.quiz.service.processor;

import io.github.maxixcom.otus.quiz.domain.Answer;
import io.github.maxixcom.otus.quiz.domain.Question;
import io.github.maxixcom.otus.quiz.domain.QuestionChoice;
import io.github.maxixcom.otus.quiz.domain.QuizQuestion;
import io.github.maxixcom.otus.quiz.service.InputOutputService;
import io.github.maxixcom.otus.quiz.service.QuestionProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionChoiceProcessor implements QuestionProcessor {
    private final InputOutputService inputOutputService;

    public QuestionChoiceProcessor(InputOutputService inputOutputService) {
        this.inputOutputService = inputOutputService;
    }

    @Override
    public void processQuestion(QuizQuestion quizQuestion) {
        Question question = quizQuestion.getQuestion();

        inputOutputService.printlnString(question.getContent());
        inputOutputService.printNewLine();

        List<Answer> options = ((QuestionChoice) question).getOptions();
        int firstIndex = 1;
        int lastIndex = options.size();
        for (int index = firstIndex; index <= lastIndex; index++) {
            inputOutputService.printlnString(
                    String.format("%d. %s", index, options.get(index - 1).getContent())
            );
        }

        inputOutputService.printNewLine();

        int answerIndex;

        while (true) {
            answerIndex = inputOutputService.readIntWithPrompt(
                    String.format("Enter number of correct question[%d-%d]: ", firstIndex, lastIndex)
            );

            if (answerIndex < firstIndex || answerIndex > lastIndex) {
                continue;
            }

            quizQuestion.giveAnswer(options.get(answerIndex - 1));

            break;
        }
    }
}
