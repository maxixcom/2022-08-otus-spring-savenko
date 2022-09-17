package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.service.InputOutputService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.stream.IntStream;

public class InputOutputServiceImpl implements InputOutputService {
    private final PrintStream printStream;
    private final Scanner scanner;

    public InputOutputServiceImpl(PrintStream printStream, InputStream inputStream) {
        this.printStream = printStream;
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public void printString(String string) {
        printStream.print(string);
    }

    @Override
    public void printlnString(String string) {
        printStream.println(string);
    }

    @Override
    public int readIntWithPrompt(String prompt) {
        while (true) {
            printStream.print(prompt);
            if (!scanner.hasNextInt()) {
                scanner.nextLine();
                continue;
            }
            break;
        }
        return scanner.nextInt();
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    private void printLine(Character character) {
        IntStream.range(0, 60).forEach(i -> printStream.print(character));
    }
}
