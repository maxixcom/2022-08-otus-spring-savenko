package io.github.maxixcom.otus.batch.batch;

import io.github.maxixcom.otus.batch.service.WarmCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WarmCacheStepConfig {
    private static final String STEP_NAME = "warmCacheStep";
    private final StepBuilderFactory stepBuilderFactory;
    private final WarmCacheService warmCacheService;

    @Bean
    public Step warmCacheStep() {
        return this.stepBuilderFactory.get(STEP_NAME)
                .tasklet(warmCacheTasklet())
                .build();
    }

    @Bean
    public MethodInvokingTaskletAdapter warmCacheTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(warmCacheService);
        adapter.setTargetMethod("warmCache");

        return adapter;
    }

}
