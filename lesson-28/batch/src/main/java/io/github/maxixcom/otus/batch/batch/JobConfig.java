package io.github.maxixcom.otus.batch.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@RequiredArgsConstructor
public class JobConfig {
    private final static String JOB_NAME = "transferDataToMongo";
    private final static String TRANSFER_AUTHORS_FLOW_NAME = "transferAuthorsFlow";
    private final static String TRANSFER_GENRES_FLOW_NAME = "transferGenresFlow";
    private final JobBuilderFactory jobBuilderFactory;

    @Bean
    Flow transferAuthorsFlow(@Qualifier("transferAuthorsStep") Step transferAuthors) {
        return new FlowBuilder<SimpleFlow>(TRANSFER_AUTHORS_FLOW_NAME)
                .start(transferAuthors)
                .build();
    }

    @Bean
    Flow transferGenresFlow(@Qualifier("transferGenresStep") Step transferGenres) {
        return new FlowBuilder<SimpleFlow>(TRANSFER_GENRES_FLOW_NAME)
                .start(transferGenres)
                .build();
    }

    @Bean
    Job transferDataToMongo(
            @Qualifier("cleanUpStep") Step cleanUpStep,
            Flow transferAuthorsFlow,
            Flow transferGenresFlow,
            Step transferBooksStep
    ) {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(cleanUpStep)
                .split(new SimpleAsyncTaskExecutor())
                .add(transferAuthorsFlow, transferGenresFlow)
                .next(transferBooksStep)
                .end()
                .build();
    }
}
