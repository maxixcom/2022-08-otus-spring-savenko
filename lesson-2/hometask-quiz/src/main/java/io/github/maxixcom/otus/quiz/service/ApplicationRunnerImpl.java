package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.domain.Question;
import io.github.maxixcom.otus.quiz.domain.QuestionChoice;
import io.github.maxixcom.otus.quiz.domain.QuestionGeneral;
import io.github.maxixcom.otus.quiz.domain.Quiz;
import io.github.maxixcom.otus.quiz.domain.QuizQuestion;
import io.github.maxixcom.otus.quiz.domain.Score;
import io.github.maxixcom.otus.quiz.domain.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ApplicationRunnerImpl implements ApplicationRunner {
    private final QuizService quizService;
    private final QuestionProcessor questionGeneralProcessor;
    private final QuestionProcessor questionChoiceProcessor;
    private final InputOutputService inputOutputService;

    public ApplicationRunnerImpl(
            QuizService quizService,
            @Qualifier("questionGeneralProcessor") QuestionProcessor questionGeneralProcessor,
            @Qualifier("questionChoiceProcessor") QuestionProcessor questionChoiceProcessor,
            InputOutputService inputOutputService
    ) {
        this.quizService = quizService;
        this.questionGeneralProcessor = questionGeneralProcessor;
        this.questionChoiceProcessor = questionChoiceProcessor;
        this.inputOutputService = inputOutputService;
    }

    @Override
    public void run() {
        inputOutputService.printNewLine();
        Student student = meetTheNewStudent();

        Quiz quiz = this.quizService.newQuizForStudent(student);

        printWelcomeMessage(student, quiz);

        inputOutputService.readLine();

        for (QuizQuestion quizQuestion : quiz) {
            inputOutputService.printThinSeparator();

            Question question = quizQuestion.getQuestion();

            if (question instanceof QuestionChoice) {
                this.questionChoiceProcessor.processQuestion(quizQuestion);
            } else if (question instanceof QuestionGeneral) {
                this.questionGeneralProcessor.processQuestion(quizQuestion);
            }
        }

        Score score = quizService.completeQuizAndGetScore(quiz);

        printResult(student, score);
    }

    private Student meetTheNewStudent() {
        inputOutputService.printlnString(";) Let's get acquainted!");
        inputOutputService.printString("What is your name? ");
        String firstName = inputOutputService.readLine();
        inputOutputService.printString("What is your family? ");
        String lastName = inputOutputService.readLine();

        return new Student(firstName, lastName);
    }

    private void printWelcomeMessage(Student student, Quiz quiz) {
        inputOutputService.printNewLines(2);
        inputOutputService.printlnString(String.format("Nice to meet you %s!", student.getFullName()));
        inputOutputService.printlnString(String.format("You are expected to answer the following %d questions.", quiz.getQuestionsCount()));
        inputOutputService.printlnString("Let's begin and good luck!");
        inputOutputService.printlnString("Press enter to start.");
        inputOutputService.printNewLine();
    }

    private void printResult(Student student, Score score) {
        inputOutputService.printThickSeparator();
        inputOutputService.printlnString(
                String.format(
                        "%s, you have answered correctly %d of %d questions",
                        student.getFullName(),
                        score.getCorrectAnswers(),
                        score.getTotalQuestions()
                )
        );
        inputOutputService.printNewLine();
        inputOutputService.printlnString("Verdict: " + (score.isPassed() ? "PASSED!!!" : "FAILED :("));
    }
}
