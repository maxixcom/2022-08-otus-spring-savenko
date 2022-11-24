package io.github.maxixcom.otus.booklib.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "io.github.maxixcom.otus.booklib.repository")
@Configuration
public class MongoConfig {
}
