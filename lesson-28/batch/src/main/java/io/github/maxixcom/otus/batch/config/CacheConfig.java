package io.github.maxixcom.otus.batch.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        /**
         * Для больших данных конечно нужно использовать что-то посерьезнее, типа Ehcache и ограничивать размеры "кэшей"
         */
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("author"),
                new ConcurrentMapCache("genre")
        ));
        return cacheManager;
    }
}
