package io.github.maxixcom.otus.booklib.service.comment;

import java.util.Set;

public interface BookCommentDeleteService {
    void deleteComment(Set<Long> commentIds);
}
