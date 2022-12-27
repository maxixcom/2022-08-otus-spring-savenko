package io.github.maxixcom.otus.enrich.messaging;

import io.github.maxixcom.otus.enrich.domain.Request;
import io.github.maxixcom.otus.enrich.domain.RichData;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface EnrichmentGateway {
    @Gateway(requestChannel = "requestChannel", replyChannel = "richDataChannel")
    RichData enrich(Request request);
}
