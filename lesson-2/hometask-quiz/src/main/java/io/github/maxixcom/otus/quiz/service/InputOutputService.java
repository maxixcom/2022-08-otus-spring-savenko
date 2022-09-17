package io.github.maxixcom.otus.quiz.service;

public interface InputOutputService {
    void printString(String string);

    void printlnString(String string);

    default void printNewLine() {
        printNewLines(1);
    }

    void printNewLines(int count);

    void printThinSeparator();

    void printThickSeparator();

    int readIntWithPrompt(String prompt);

    String readLine();
}
