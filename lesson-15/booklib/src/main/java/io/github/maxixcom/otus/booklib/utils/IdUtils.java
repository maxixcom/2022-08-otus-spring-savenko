package io.github.maxixcom.otus.booklib.utils;

import io.github.maxixcom.otus.booklib.exception.InvalidIdException;
import org.bson.types.ObjectId;

public class IdUtils {
    public static ObjectId stringToObjectId(String id) {
        try {
            return new ObjectId(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidIdException(String.format("You passed wrong id [%s]", id));
        }
    }
}
