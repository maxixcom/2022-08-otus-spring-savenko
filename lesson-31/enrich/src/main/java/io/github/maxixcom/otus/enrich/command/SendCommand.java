package io.github.maxixcom.otus.enrich.command;

import io.github.maxixcom.otus.enrich.service.SendService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@ShellCommandGroup("Messaging")
@RequiredArgsConstructor
public class SendCommand {
    private final SendService sendService;

    @ShellMethod(value = "Send messages into gateway", key = {"send", "s"})
    void send(@ShellOption(defaultValue = "10") int num) {
        sendService.send(num);
    }
}
