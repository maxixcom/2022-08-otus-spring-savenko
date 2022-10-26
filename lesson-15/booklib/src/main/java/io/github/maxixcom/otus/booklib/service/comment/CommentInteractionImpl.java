package io.github.maxixcom.otus.booklib.service.comment;

import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentInteractionImpl implements CommentInteraction {
    private final IOService ioService;

    @Override
    public String collectCommentCreateInfo() {
        return ioService.readLineWithPrompt("Enter you comment: ");
    }

    @Override
    public String collectCommentUpdateInfo() {
        return ioService.readLineWithPrompt("Enter new text of comment: ");
    }
}
