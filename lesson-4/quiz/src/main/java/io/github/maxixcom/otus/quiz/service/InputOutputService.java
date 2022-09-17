package io.github.maxixcom.otus.quiz.service;

public interface InputOutputService {
    void printString(String string);

    void printlnString(String string);

    default void printNewLine() {
        printlnString(String.format("%n"));
    }

    int readIntWithPrompt(String prompt);

    String readLine();
}
