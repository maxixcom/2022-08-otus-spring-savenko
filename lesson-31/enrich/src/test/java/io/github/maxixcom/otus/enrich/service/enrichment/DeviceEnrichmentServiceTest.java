package io.github.maxixcom.otus.enrich.service.enrichment;

import io.github.maxixcom.otus.enrich.domain.Request;
import io.github.maxixcom.otus.enrich.domain.RichData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DeviceEnrichmentService.class)
class DeviceEnrichmentServiceTest {
    @Autowired
    private DeviceEnrichmentService deviceEnrichmentService;

    @Test
    void shouldFillDataWithDeviceInfo() {
        RichData in = RichData.fromRequest(new Request(
                null,
                "Mozilla/5.0 (iPhone; CPU iPhone OS 15_6_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.6.1 Mobile/15E148 Safari/604.1"
        ));
        RichData out = deviceEnrichmentService.enrich(in);

        Assertions.assertThat(out.getDeviceType()).isNotNull();
        Assertions.assertThat(out.getOsGroup()).isNotNull();
        Assertions.assertThat(out.getOsManufacture()).isNotNull();
        Assertions.assertThat(out.getOsName()).isNotNull();
        Assertions.assertThat(out.getBrowserGroup()).isNotNull();
        Assertions.assertThat(out.getBrowserName()).isNotNull();
        Assertions.assertThat(out.getBrowserVersion()).isNotNull();
    }
}