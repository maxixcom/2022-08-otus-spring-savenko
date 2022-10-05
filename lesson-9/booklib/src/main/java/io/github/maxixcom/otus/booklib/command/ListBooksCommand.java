package io.github.maxixcom.otus.booklib.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ListBooksCommand {
    @ShellMethod
    public void listBooks() {
        System.out.println("List books!");
    }
}
