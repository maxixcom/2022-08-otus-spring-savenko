package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.domain.QuestionChoice;
import io.github.maxixcom.otus.quiz.domain.QuestionGeneral;

import java.io.PrintStream;

public class OutputServiceImpl implements OutputService {
    private final PrintStream printStream;

    public OutputServiceImpl(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void printHeader() {
        printStream.println("===============================================");
        printStream.println("#  List of the Quiz questions:");
        printStream.println("-----------------------------------------------");
    }

    @Override
    public void printQuestionGeneral(QuestionGeneral question, Integer index) {
        printStream.println(question.getContent() + "?");
    }

    @Override
    public void printQuestionChoice(QuestionChoice question, Integer index) {
        printStream.printf("%s? (%s)%n",
                question.getContent(),
                String.join(",", question.getOptions())
        );
    }

    @Override
    public void printNoQuestion() {
        printStream.println("There are no questions yet ...");
    }


}
