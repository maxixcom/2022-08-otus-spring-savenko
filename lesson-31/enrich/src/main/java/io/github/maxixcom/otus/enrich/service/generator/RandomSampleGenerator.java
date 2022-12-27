package io.github.maxixcom.otus.enrich.service.generator;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public abstract class RandomSampleGenerator<T> implements ValueGenerator<T> {
    private final Random random = new Random();

    protected abstract List<T> getSamples();

    @Override
    public Stream<T> generate() {
        List<T> samples = getSamples();
        return Stream.generate(() -> {
            int index = random.nextInt(samples.size());
            return samples.get(index);
        });
    }
}
