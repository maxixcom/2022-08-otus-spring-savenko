package io.github.maxixcom.otus.batch.batch;

import io.github.maxixcom.otus.batch.config.provider.AuthorsCollectionProvider;
import io.github.maxixcom.otus.batch.domain.Author;
import io.github.maxixcom.otus.batch.mongo.document.AuthorDocument;
import io.github.maxixcom.otus.batch.repository.AuthorRepository;
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
public class TransferAuthorsStepConfig {
    private static final String STEP_NAME = "transferAuthorsStep";
    private static final String ITEM_READER_NAME = "authorItemReader";
    private static final int CHUNK_SIZE = 5;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Step transferAuthorsStep(
            RepositoryItemReader<Author> authorRepositoryItemReader,
            ItemProcessor<Author, AuthorDocument> authorItemProcessor,
            ItemWriter<AuthorDocument> authorItemWriter
    ) {
        return this.stepBuilderFactory.get(STEP_NAME)
                .<Author, AuthorDocument>chunk(CHUNK_SIZE)
                .reader(authorRepositoryItemReader)
                .processor(authorItemProcessor)
                .writer(authorItemWriter)
                .build();
    }

    @Bean
    RepositoryItemReader<Author> authorRepositoryItemReader(
            AuthorRepository authorRepository
    ) {
        RepositoryItemReaderBuilder<Author> builder = new RepositoryItemReaderBuilder<>();
        return builder.repository(authorRepository)
                .name(ITEM_READER_NAME)
                .methodName("findAll")
                .arguments(Collections.emptyList())
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    ItemProcessor<Author, AuthorDocument> authorItemProcessor() {
        return AuthorDocument::fromDomainObject;
    }

    @Bean
    ItemWriter<AuthorDocument> authorItemWriter(MongoTemplate mongoTemplate, AuthorsCollectionProvider authorsCollectionProvider) {
        return new MongoItemWriterBuilder<AuthorDocument>()
                .template(mongoTemplate)
                .collection(authorsCollectionProvider.getAuthorsCollection())
                .build();
    }
}
