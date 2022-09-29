package io.github.maxixcom.otus.quiz.command;

import io.github.maxixcom.otus.quiz.service.InputOutputService;
import io.github.maxixcom.otus.quiz.service.QuizRunner;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class QuizCommand {
    private final QuizRunner quizRunner;
    private final InputOutputService inputOutputService;

    private boolean isRegistered = false;

    public QuizCommand(QuizRunner quizRunner, InputOutputService inputOutputService) {
        this.quizRunner = quizRunner;
        this.inputOutputService = inputOutputService;
    }

    @ShellMethod(value = "Запуск тестирования - Quiz", key = {"q", "quiz", "run-quiz"})
    @ShellMethodAvailability(value = "isRegistered")
    public void runQuiz() {
        quizRunner.run();
    }

    public Availability isRegistered() {
        return isRegistered
                ? Availability.available()
                : Availability.unavailable("\'Вам необходимо зарегистрироваться\'");
    }

    @ShellMethod(value = "Регистрация на прохождение тестирования", key = {"r", "register"})
    public void register() {
        this.isRegistered = true;
        inputOutputService.printlnString("Вы зарегистрированы");
    }
}
