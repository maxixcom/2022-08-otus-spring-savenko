package io.github.maxixcom.otus.quiz.service.processor;

import io.github.maxixcom.otus.quiz.domain.Answer;
import io.github.maxixcom.otus.quiz.domain.Question;
import io.github.maxixcom.otus.quiz.domain.QuestionChoice;
import io.github.maxixcom.otus.quiz.service.InputOutputService;
import io.github.maxixcom.otus.quiz.service.QuestionAnswerResult;
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
    public Class<QuestionChoice> getSupportedQuestionClass() {
        return QuestionChoice.class;
    }

    @Override
    public QuestionAnswerResult processQuestion(Question question) {
        inputOutputService.printlnString(question.getContent());
        inputOutputService.printNewLine();

        List<Answer> options = ((QuestionChoice) question).getOptions();
        int firstQuestionIndex = 1;
        int lastQuestionIndex = options.size();

        listQuestionOptions(options, firstQuestionIndex, lastQuestionIndex);

        inputOutputService.printNewLine();

        Answer answer = getStudentAnswer(options, firstQuestionIndex, lastQuestionIndex);

        return new QuestionAnswerResult(question, answer);

    }

    private void listQuestionOptions(List<Answer> options, int firstQuestionIndex, int lastQuestionIndex) {
        for (int index = firstQuestionIndex; index <= lastQuestionIndex; index++) {
            inputOutputService.printlnString(
                    String.format("%d. %s", index, options.get(index - 1).getContent())
            );
        }
    }

    private Answer getStudentAnswer(List<Answer> options, int firstQuestionIndex, int lastQuestionIndex) {
        int answerIndex;
        while (true) {
            answerIndex = inputOutputService.readIntWithPrompt(
                    String.format("Enter number of correct question[%d-%d]: ", firstQuestionIndex, lastQuestionIndex)
            );

            if (answerIndex < firstQuestionIndex || answerIndex > lastQuestionIndex) {
                continue;
            }

            return options.get(answerIndex - 1);
        }
    }
}
