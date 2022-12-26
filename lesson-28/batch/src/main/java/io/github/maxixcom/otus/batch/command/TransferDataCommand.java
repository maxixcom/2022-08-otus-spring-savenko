package io.github.maxixcom.otus.batch.command;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@ShellCommandGroup("Batch transfer")
@RequiredArgsConstructor
public class TransferDataCommand {
    private final JobOperator jobOperator;

    @ShellMethod(value = "Transfer data to mongo database", key = {"t", "transfer"})
    public void startTransfer() throws Exception {

        Long executionId = jobOperator.start("transferDataToMongo", "TS=" + System.nanoTime());
        System.out.println(jobOperator.getSummary(executionId));
    }
}
