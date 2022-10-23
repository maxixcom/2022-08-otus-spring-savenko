package io.github.maxixcom.otus.booklib.config;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongock
@EnableMongoRepositories(basePackages = "io.github.maxixcom.otus.booklib.repository")
@Configuration
public class MongoConfig {
}
