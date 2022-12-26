package io.github.maxixcom.otus.batch.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "io.github.maxixcom.otus.batch.repository")
@EnableConfigurationProperties(
        TargetConfigurationProperties.class
)
public class AppConfig {
}
