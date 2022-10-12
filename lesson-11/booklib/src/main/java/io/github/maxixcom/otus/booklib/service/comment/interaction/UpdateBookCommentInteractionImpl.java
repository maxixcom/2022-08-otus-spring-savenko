package io.github.maxixcom.otus.booklib.service.comment.interaction;

import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateBookCommentInteractionImpl implements UpdateBookCommentInteraction {
    private final IOService ioService;

    @Override
    public String collectBookCommentInfo() {
        return ioService.readLineWithPrompt("Enter new text of comment: ");
    }
}
