package io.github.maxixcom.otus.quiz.dao.csv;

import io.github.maxixcom.otus.quiz.dao.QuestionLoader;
import io.github.maxixcom.otus.quiz.domain.Answer;
import io.github.maxixcom.otus.quiz.domain.Question;
import io.github.maxixcom.otus.quiz.domain.QuestionChoice;
import io.github.maxixcom.otus.quiz.domain.QuestionGeneral;
import io.github.maxixcom.otus.quiz.exceptions.UnknownQuestionTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionCsvResourceLoader implements QuestionLoader {
    private final static int CSV_RECORD_COLUMNS_COUNT = 4;
    private final static int CSV_COLUMN_INDEX_TYPE = 0;
    private final static int CSV_COLUMN_INDEX_QUESTION = 1;
    private final static int CSV_COLUMN_INDEX_OPTIONS = 2;
    private final static int CSV_COLUMN_INDEX_ANSWER = 3;

    private final String questionFile;

    public QuestionCsvResourceLoader(@Value("${quiz.questions.file}") String questionFile) {
        this.questionFile = questionFile;
    }

    @Override
    public List<Question> load() {
        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(this.questionFile);

        Assert.notNull(inputStream, "Resource " + this.questionFile + " not found");

        try (inputStream) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                return reader.lines()
                        .map(line -> Arrays.asList(line.split(";")))
                        .filter(this::validateCsvRecord)
                        .map(this::convertCsvRecordToQuestion)
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Boolean validateCsvRecord(List<String> columns) {
        if (columns.size() != CSV_RECORD_COLUMNS_COUNT) {
            return false;
        }

        CsvRecordType csvRecordType = CsvRecordType.fromStringOrNull(
                columns.get(CSV_COLUMN_INDEX_TYPE)
        );

        return csvRecordType != null;
    }


    private Question convertCsvRecordToQuestion(List<String> columns) {
        String typeColumnValue = columns.get(CSV_COLUMN_INDEX_TYPE);

        CsvRecordType csvRecordType = CsvRecordType.fromStringOrNull(typeColumnValue);

        if (csvRecordType == null) {
            throw new UnknownQuestionTypeException(typeColumnValue);
        }

        switch (csvRecordType) {
            case General:
                return convertCsvRecordToQuestionGeneral(columns);
            case Choice:
                return convertCsvRecordToQuestionChoice(columns);
        }

        // Theoretically unreachable point if programmer is good one and have added all the switch cases ;)
        throw new IllegalStateException("Can't find enum for question type: " + typeColumnValue);
    }

    private QuestionGeneral convertCsvRecordToQuestionGeneral(List<String> columns) {
        return new QuestionGeneral(
                columns.get(CSV_COLUMN_INDEX_QUESTION),
                new Answer(columns.get(CSV_COLUMN_INDEX_ANSWER))
        );
    }

    private QuestionChoice convertCsvRecordToQuestionChoice(List<String> columns) {
        String answerOptionsColumnValue = columns.get(CSV_COLUMN_INDEX_OPTIONS);

        List<Answer> options = Arrays.stream(answerOptionsColumnValue.split(","))
                .map(Answer::new)
                .collect(Collectors.toList());

        int correctAnswerIndex = Integer.parseInt(columns.get(CSV_COLUMN_INDEX_ANSWER));

        return new QuestionChoice(
                columns.get(CSV_COLUMN_INDEX_QUESTION),
                options.get(correctAnswerIndex),
                options
        );
    }
}
