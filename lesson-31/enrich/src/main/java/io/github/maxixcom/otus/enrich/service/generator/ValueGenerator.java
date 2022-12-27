package io.github.maxixcom.otus.enrich.service.generator;

import java.util.stream.Stream;

public interface ValueGenerator<T> {
    Stream<T> generate();
}
