package io.github.maxixcom.otus.booklib.service;

import java.util.Set;

public interface BookDeleteService {
    void deleteBooks(Set<Long> bookIds);
}
