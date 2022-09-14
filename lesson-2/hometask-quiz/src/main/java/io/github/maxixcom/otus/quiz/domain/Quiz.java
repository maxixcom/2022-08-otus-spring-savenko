package io.github.maxixcom.otus.quiz.domain;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Quiz implements Iterable<Question> {
    private final List<Question> questions;
    private final Student student;

    public Quiz(List<Question> questions, Student student) {
        this.questions = questions;
        this.student = student;
    }

    public int getQuestionsCount() {
        return questions.size();
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public Iterator<Question> iterator() {
        return questions.iterator();
    }

    @Override
    public void forEach(Consumer<? super Question> action) {
        questions.forEach(action);
    }

    @Override
    public Spliterator<Question> spliterator() {
        return questions.spliterator();
    }
}
