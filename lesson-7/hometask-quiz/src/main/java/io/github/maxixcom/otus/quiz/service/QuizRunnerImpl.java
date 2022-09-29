package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.domain.Question;
import io.github.maxixcom.otus.quiz.domain.Quiz;
import io.github.maxixcom.otus.quiz.domain.Score;
import io.github.maxixcom.otus.quiz.domain.Student;
import org.springframework.stereotype.Component;

@Component
public class QuizRunnerImpl implements QuizRunner {
    private final QuizService quizService;
    private final InputOutputService inputOutputService;
    private final QuestionProcessorManager questionProcessorManager;
    private final TranslationService translationService;

    public QuizRunnerImpl(
            QuizService quizService,
            InputOutputService inputOutputService,
            QuestionProcessorManager questionProcessorManager,
            TranslationService translationService) {
        this.quizService = quizService;
        this.inputOutputService = inputOutputService;
        this.questionProcessorManager = questionProcessorManager;
        this.translationService = translationService;
    }

    @Override
    public void run() {
        inputOutputService.printNewLine();
        Student student = meetTheNewStudent();

        Quiz quiz = this.quizService.newQuizForStudent(student);

        printWelcomeMessage(student, quiz);

        inputOutputService.readLine();

        int correctAnswers = 0;

        for (Question question : quiz) {
            inputOutputService.printlnString("----------------------------------");

            final QuestionAnswerResult result = questionProcessorManager.processQuestion(question);
            if (result.isTheAnswerCorrect()) {
                correctAnswers++;
            }
        }

        Score score = quizService.completeQuizAndGetScore(quiz, correctAnswers);

        printResult(student, score);
    }

    private Student meetTheNewStudent() {
        inputOutputService.printlnString(translationService.translate("meet_line_1"));
        inputOutputService.printString(translationService.translate("meet_line_2") + " ");
        String firstName = inputOutputService.readLine();
        inputOutputService.printString(translationService.translate("meet_line_3") + " ");
        String lastName = inputOutputService.readLine();

        return new Student(firstName, lastName);
    }

    private void printWelcomeMessage(Student student, Quiz quiz) {
        inputOutputService.printNewLine();
        inputOutputService.printNewLine();
        inputOutputService.printlnString(translationService.translate("welcome_line_1", student.getFullName()));
        inputOutputService.printlnString(translationService.translate("welcome_line_2", quiz.getQuestionsCount()));
        inputOutputService.printlnString(translationService.translate("welcome_line_3"));
        inputOutputService.printlnString(translationService.translate("welcome_line_4"));
        inputOutputService.printNewLine();
    }

    private void printResult(Student student, Score score) {
        inputOutputService.printlnString("----------------------------------");
        inputOutputService.printlnString(
                translationService.translate(
                        "result_line_1",
                        student.getFullName(),
                        score.getCorrectAnswers(),
                        score.getTotalQuestions()
                )
        );
        inputOutputService.printNewLine();
        String verdict = (score.isPassed()
                ? translationService.translate("result_passed")
                : translationService.translate("result_failed"));
        inputOutputService.printlnString(translationService.translate("result_line_2", verdict));
    }
}
