package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.domain.Question;
import io.github.maxixcom.otus.quiz.exceptions.QuestionProcessorNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuestionProcessorManager {
    private final Map<Class<?>, QuestionProcessor> questionProcessorMap = new HashMap<>();

    public QuestionProcessorManager(List<QuestionProcessor> questionProcessorList
    ) {
        for (QuestionProcessor questionProcessor : questionProcessorList) {
            questionProcessorMap.put(
                    questionProcessor.getSupportedQuestionClass(),
                    questionProcessor
            );
        }
    }

    public QuestionAnswerResult processQuestion(Question question) {
        QuestionProcessor questionProcessor = Optional.ofNullable(questionProcessorMap.get(question.getClass()))
                .orElseThrow(() -> new QuestionProcessorNotFoundException("Question processor not found for type: " + question.getClass()));
        return questionProcessor.processQuestion(question);
    }

}
