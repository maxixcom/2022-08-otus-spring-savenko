package io.github.maxixcom.otus.batch.batch;

import io.github.maxixcom.otus.batch.service.CleanUpTargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CleanUpStepConfig {
    private static final String STEP_NAME = "cleanUpStep";
    private final StepBuilderFactory stepBuilderFactory;
    private final CleanUpTargetService cleanUpTargetService;

    @Bean
    public Step cleanUpStep() {
        return this.stepBuilderFactory.get(STEP_NAME)
                .tasklet(cleanUpTargetTasklet())
                .build();
    }

    @Bean
    public MethodInvokingTaskletAdapter cleanUpTargetTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(cleanUpTargetService);
        adapter.setTargetMethod("cleanUp");

        return adapter;
    }

}
