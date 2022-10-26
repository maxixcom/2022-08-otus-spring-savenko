package io.github.maxixcom.otus.booklib.service.comment;

import io.github.maxixcom.otus.booklib.domain.Comment;
import io.github.maxixcom.otus.booklib.exception.BookNotFoundException;
import io.github.maxixcom.otus.booklib.repository.BookRepository;
import io.github.maxixcom.otus.booklib.repository.CommentRepository;
import io.github.maxixcom.otus.booklib.utils.IdUtils;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAllBookComments(String bookId) {
        return commentRepository.findAllByBookId(IdUtils.stringToObjectId(bookId));
    }

    @Transactional
    @Override
    public void createBookComment(String bookId, String commentText) {
        ObjectId id = IdUtils.stringToObjectId(bookId);
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book #" + bookId + " not found");
        }
        commentRepository.save(new Comment(null, id, commentText));
    }

    @Transactional
    @Override
    public void updateBookComment(String commentId, String commentText) {
        commentRepository.findById(IdUtils.stringToObjectId(commentId))
                .map(bookComment -> {
                    bookComment.setComment(commentText);
                    return bookComment;
                })
                .ifPresent(commentRepository::save);
    }

    @Transactional
    @Override
    public void deleteBookComments(Set<String> commentIds) {
        List<ObjectId> ids = commentIds.stream()
                .map(IdUtils::stringToObjectId)
                .collect(Collectors.toList());
        commentRepository.deleteAllById(ids);
    }
}
