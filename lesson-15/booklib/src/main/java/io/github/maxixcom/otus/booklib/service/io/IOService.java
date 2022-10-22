package io.github.maxixcom.otus.booklib.service.io;

public interface IOService {
    void out(String format, Object... arg);

    int readIntWithPrompt(String formattedPrompt, Object... args);

    String readLineWithPrompt(String formattedPrompt, Object... args);
}
