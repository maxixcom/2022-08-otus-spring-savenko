package io.github.maxixcom.otus.quiz.service.processor;

import io.github.maxixcom.otus.quiz.domain.Answer;
import io.github.maxixcom.otus.quiz.domain.Question;
import io.github.maxixcom.otus.quiz.domain.QuestionGeneral;
import io.github.maxixcom.otus.quiz.service.InputOutputService;
import io.github.maxixcom.otus.quiz.service.QuestionAnswerResult;
import io.github.maxixcom.otus.quiz.service.QuestionProcessor;
import io.github.maxixcom.otus.quiz.service.TranslationService;
import org.springframework.stereotype.Component;

@Component
public class QuestionGeneralProcessor implements QuestionProcessor {
    private final InputOutputService inputOutputService;
    private final TranslationService translationService;

    public QuestionGeneralProcessor(InputOutputService inputOutputService, TranslationService translationService) {
        this.inputOutputService = inputOutputService;
        this.translationService = translationService;
    }

    @Override
    public Class<QuestionGeneral> getSupportedQuestionClass() {
        return QuestionGeneral.class;
    }

    @Override
    public QuestionAnswerResult processQuestion(Question question) {

        inputOutputService.printlnString(translationService.translate(question.getContent()));
        inputOutputService.printNewLine();

        inputOutputService.printString(translationService.translate("processor_general_line_1"));

        Answer answer = getStudentAnswer();

        return new QuestionAnswerResult(question, answer);
    }

    private Answer getStudentAnswer() {
        while (true) {
            String userInput = inputOutputService.readLine().trim();
            if (userInput.isBlank()) {
                continue;
            }
            return new Answer(userInput);

        }
    }
}
