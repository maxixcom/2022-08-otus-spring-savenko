package io.github.maxixcom.otus.enrich.service.enrichment;

import io.github.maxixcom.otus.enrich.domain.RichData;

public interface EnrichmentService {
    RichData enrich(RichData richData);
}
