package io.github.maxixcom.otus.quiz.dao.csv;

import io.github.maxixcom.otus.quiz.dao.QuestionLoader;
import io.github.maxixcom.otus.quiz.domain.Question;
import io.github.maxixcom.otus.quiz.domain.QuestionChoice;
import io.github.maxixcom.otus.quiz.domain.QuestionGeneral;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionCsvResourceLoader implements QuestionLoader {
    @Override
    public List<Question> load() {
        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("quiz/questions.csv");

        Assert.notNull(inputStream, "Resource quiz/questions.csv not found");

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines()
                .map(line -> Arrays.asList(line.split(";")))
                .filter(this::validateCsvRecord)
                .map(this::convertCsvRecordToQuestion)
                .collect(Collectors.toList());
    }

    private Boolean validateCsvRecord(@NonNull List<String> columns) {
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

    private Boolean validateRecordOfChoiceType(@NonNull List<String> columns) {
        if (columns.size() < 3) {
            return false;
        }
        return true;
    }

    private Boolean validateRecordOfGeneralType(@NonNull List<String> columns) {
        if (columns.size() < 2) {
            return false;
        }
        return true;
    }

    private Question convertCsvRecordToQuestion(@NonNull List<String> columns) {
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

    private QuestionGeneral convertCsvRecordToQuestionGeneral(@NonNull List<String> columns) {
        return new QuestionGeneral(columns.get(1));
    }

    private QuestionChoice convertCsvRecordToQuestionChoice(@NonNull List<String> columns) {
        Assert.state(columns.size() > 1, "Too low column size for type choice: " + columns.size());

        List<String> options = Arrays.asList(columns.get(2).split(","));
        return new QuestionChoice(columns.get(1), options);
    }
}
