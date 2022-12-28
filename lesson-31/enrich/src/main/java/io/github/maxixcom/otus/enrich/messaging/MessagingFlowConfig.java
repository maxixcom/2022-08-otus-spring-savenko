package io.github.maxixcom.otus.enrich.messaging;

import io.github.maxixcom.otus.enrich.domain.RichData;
import io.github.maxixcom.otus.enrich.service.enrichment.DeviceEnrichmentService;
import io.github.maxixcom.otus.enrich.service.enrichment.GeoEnrichmentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;

@Configuration
public class MessagingFlowConfig {

    @Bean
    MessageChannel richDataChannel() {
        PublishSubscribeChannel channel = new PublishSubscribeChannel();
        return channel;
    }

    @Bean
    IntegrationFlow flow(
            GeoEnrichmentService geoEnrichmentService,
            DeviceEnrichmentService deviceEnrichmentService
    ) {
        return IntegrationFlows.from("requestChannel")
                .transform(RichData::fromRequest)
                .handle(geoEnrichmentService, "enrich")
                .handle(deviceEnrichmentService, "enrich")
                .channel(richDataChannel())
                .get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
    }

}
