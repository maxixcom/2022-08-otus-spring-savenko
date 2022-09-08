package io.github.maxixcom.otus.quiz.dao.csv;

import io.github.maxixcom.otus.quiz.dao.QuestionLoader;
import io.github.maxixcom.otus.quiz.domain.Question;
import io.github.maxixcom.otus.quiz.domain.QuestionChoice;
import io.github.maxixcom.otus.quiz.domain.QuestionGeneral;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionCsvResourceLoader implements QuestionLoader {
    private final String questionFile;

    public QuestionCsvResourceLoader(String questionFile) {
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
        if (columns.isEmpty()) {
            return false;
        }

        CsvRecordType csvRecordType = CsvRecordType.fromStringOrNull(columns.get(0));

        if (csvRecordType == null) {
            return false;
        }

        switch (csvRecordType) {
            case General:
                return validateRecordOfGeneralType(columns);
            case Choice:
                return validateRecordOfChoiceType(columns);
        }

        return false;
    }

    private Boolean validateRecordOfChoiceType(List<String> columns) {
        if (columns.size() < 3) {
            return false;
        }
        return true;
    }

    private Boolean validateRecordOfGeneralType(List<String> columns) {
        if (columns.size() < 2) {
            return false;
        }
        return true;
    }

    private Question convertCsvRecordToQuestion(List<String> columns) {
        CsvRecordType csvRecordType = CsvRecordType.fromStringOrNull(columns.get(0));

        Assert.state(csvRecordType != null, "Undefined csv record type");

        switch (csvRecordType) {
            case General:
                return convertCsvRecordToQuestionGeneral(columns);
            case Choice:
                return convertCsvRecordToQuestionChoice(columns);
        }

        Assert.isTrue(true, "Unknown csv record type: " + csvRecordType.getCode());

        return null;
    }

    private QuestionGeneral convertCsvRecordToQuestionGeneral(List<String> columns) {
        return new QuestionGeneral(columns.get(1));
    }

    private QuestionChoice convertCsvRecordToQuestionChoice(List<String> columns) {
        Assert.state(columns.size() > 1, "Too low column size for type choice: " + columns.size());

        List<String> options = Arrays.asList(columns.get(2).split(","));
        return new QuestionChoice(columns.get(1), options);
    }
}
