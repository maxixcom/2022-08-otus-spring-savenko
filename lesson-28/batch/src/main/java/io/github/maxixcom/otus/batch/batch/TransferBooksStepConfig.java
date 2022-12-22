package io.github.maxixcom.otus.batch.batch;

import io.github.maxixcom.otus.batch.config.provider.BooksCollectionProvider;
import io.github.maxixcom.otus.batch.domain.Book;
import io.github.maxixcom.otus.batch.mongo.document.BookDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class TransferBooksStepConfig {
    private static final String STEP_NAME = "transferBooksStep";
    private static final String ITEM_READER_NAME = "bookItemReader";
    private static final int CHUNK_SIZE = 5;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    Step transferBooksStep(
            JpaPagingItemReader<Book> bookItemReader,
            ItemProcessor<Book, BookDocument> bookItemProcessor,
            ItemWriter<BookDocument> bookItemWriter
    ) {
        return this.stepBuilderFactory.get(STEP_NAME)
                .<Book, BookDocument>chunk(CHUNK_SIZE)
                .reader(bookItemReader)
                .processor(bookItemProcessor)
                .writer(bookItemWriter)
                .build();
    }

    @Bean
    JpaPagingItemReader<Book> bookItemReader(
            EntityManagerFactory entityManagerFactory
    ) {
        JpaPagingItemReaderBuilder<Book> builder = new JpaPagingItemReaderBuilder<>();
        String query = "select distinct b from Book b left join fetch b.author left join fetch b.genre";
        return builder
                .name(ITEM_READER_NAME)
                .entityManagerFactory(entityManagerFactory)
                .queryString(query)
                .pageSize(5)
                .build();
    }

    @Bean
    ItemWriter<BookDocument> bookItemWriter(MongoTemplate mongoTemplate, BooksCollectionProvider booksCollectionProvider) {
        return new MongoItemWriterBuilder<BookDocument>()
                .template(mongoTemplate)
                .collection(booksCollectionProvider.getBooksCollection())
                .build();
    }
}
