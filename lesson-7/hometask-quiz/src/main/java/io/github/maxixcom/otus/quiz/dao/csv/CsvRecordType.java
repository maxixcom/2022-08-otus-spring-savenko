package io.github.maxixcom.otus.quiz.dao.csv;

import java.util.Arrays;

public enum CsvRecordType {
    General("general"),
    Choice("choice");

    private final String code;

    CsvRecordType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static CsvRecordType fromStringOrNull(String str) {
        return Arrays.stream(CsvRecordType.values())
                .filter(type -> type.code.equals(str))
                .findAny()
                .orElse(null);
    }
}
