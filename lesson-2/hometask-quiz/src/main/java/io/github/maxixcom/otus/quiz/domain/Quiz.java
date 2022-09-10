package io.github.maxixcom.otus.quiz.domain;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Quiz implements Iterable<QuizQuestion> {
    private final List<QuizQuestion> questions;
    private final Student student;

    public Quiz(List<Question> questions, Student student) {
        this.questions = questions.stream()
                .map(QuizQuestion::new)
                .collect(Collectors.toList());
        this.student = student;
    }

    public int getQuestionsCount() {
        return questions.size();
    }

    public int getCorrectAnswers() {
        return (int) questions.stream()
                .filter(QuizQuestion::isAnsweredCorrectly)
                .count();
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public Iterator<QuizQuestion> iterator() {
        return questions.iterator();
    }

    @Override
    public void forEach(Consumer<? super QuizQuestion> action) {
        questions.forEach(action);
    }

    @Override
    public Spliterator<QuizQuestion> spliterator() {
        return questions.spliterator();
    }
}
