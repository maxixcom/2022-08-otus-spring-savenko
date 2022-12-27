package io.github.maxixcom.otus.enrich.service;

import io.github.maxixcom.otus.enrich.domain.RichData;
import io.github.maxixcom.otus.enrich.messaging.EnrichmentGateway;
import io.github.maxixcom.otus.enrich.service.generator.RequestGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendServiceImpl implements SendService {
    private final EnrichmentGateway enrichmentGateway;
    private final RequestGenerator requestGenerator;

    @Override
    public void send(int numberOfMessages) {
        requestGenerator.generate().limit(numberOfMessages).forEach(request -> {
            RichData richData = enrichmentGateway.enrich(request);
            System.out.println(richData);
        });
    }
}
