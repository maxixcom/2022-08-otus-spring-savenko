package io.github.maxixcom.otus.enrich.service.enrichment;

import io.github.maxixcom.otus.enrich.domain.Request;
import io.github.maxixcom.otus.enrich.domain.RichData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = GeoEnrichmentService.class)
class GeoEnrichmentServiceTest {
    @Autowired
    private GeoEnrichmentService geoEnrichmentService;

    @Test
    void shouldFillDataWithCorrectCountry() {

        RichData in = RichData.fromRequest(new Request("176.106.245.30", null));
        RichData out = geoEnrichmentService.enrich(in);

        Assertions.assertThat(out.getCountry()).isNotNull();
        Assertions.assertThat(out.getCountry()).isEqualTo("RU");

    }

    @Test
    void shouldFillDataWithUnknownCountry() {

        RichData in = RichData.fromRequest(new Request("10.10.10.10", null));
        RichData out = geoEnrichmentService.enrich(in);

        Assertions.assertThat(out.getCountry()).isNotNull();
        Assertions.assertThat(out.getCountry()).isEqualTo("UNKNOWN");

    }
}