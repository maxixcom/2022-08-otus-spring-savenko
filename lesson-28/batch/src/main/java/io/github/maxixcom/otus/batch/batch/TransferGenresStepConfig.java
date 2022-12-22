package io.github.maxixcom.otus.batch.batch;

import io.github.maxixcom.otus.batch.config.provider.GenresCollectionProvider;
import io.github.maxixcom.otus.batch.domain.Genre;
import io.github.maxixcom.otus.batch.mongo.document.GenreDocument;
import io.github.maxixcom.otus.batch.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Collections;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class TransferGenresStepConfig {
    private static final String STEP_NAME = "transferGenresStep";
    private static final String ITEM_READER_NAME = "genreItemReader";
    private static final int CHUNK_SIZE = 5;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Step transferGenresStep(
            RepositoryItemReader<Genre> genreRepositoryItemReader,
            ItemProcessor<Genre, GenreDocument> genreItemProcessor,
            ItemWriter<GenreDocument> genreItemWriter
    ) {
        return this.stepBuilderFactory.get(STEP_NAME)
                .<Genre, GenreDocument>chunk(CHUNK_SIZE)
                .reader(genreRepositoryItemReader)
                .processor(genreItemProcessor)
                .writer(genreItemWriter)
                .build();
    }

    @Bean
    RepositoryItemReader<Genre> genreRepositoryItemReader(
            GenreRepository genreRepository
    ) {
        RepositoryItemReaderBuilder<Genre> builder = new RepositoryItemReaderBuilder<>();
        return builder.repository(genreRepository)
                .name(ITEM_READER_NAME)
                .methodName("findAll")
                .arguments(Collections.emptyList())
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    ItemProcessor<Genre, GenreDocument> genreItemProcessor() {
        return GenreDocument::fromDomainObject;
    }

    @Bean
    ItemWriter<GenreDocument> genreItemWriter(MongoTemplate mongoTemplate, GenresCollectionProvider genresCollectionProvider) {
        return new MongoItemWriterBuilder<GenreDocument>()
                .template(mongoTemplate)
                .collection(genresCollectionProvider.getGenresCollection())
                .build();
    }
}
