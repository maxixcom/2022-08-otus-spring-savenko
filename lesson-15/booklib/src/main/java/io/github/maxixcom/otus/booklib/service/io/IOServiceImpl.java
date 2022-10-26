package io.github.maxixcom.otus.booklib.service.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceImpl implements IOService {
    private final PrintStream printStream;
    private final Scanner scanner;

    public IOServiceImpl(PrintStream printStream, InputStream inputStream) {
        this.printStream = printStream;
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public void out(String format, Object... args) {
        printStream.printf(format, args);
    }

    @Override
    public int readIntWithPrompt(String formattedPrompt, Object... args) {
        while (true) {
            out(formattedPrompt, args);
            if (!scanner.hasNextInt()) {
                scanner.nextLine();
                continue;
            }
            break;
        }
        int value = scanner.nextInt();
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        return value;
    }

    @Override
    public String readLineWithPrompt(String formattedPrompt, Object... args) {
        out(formattedPrompt, args);
        return scanner.nextLine();
    }
}
