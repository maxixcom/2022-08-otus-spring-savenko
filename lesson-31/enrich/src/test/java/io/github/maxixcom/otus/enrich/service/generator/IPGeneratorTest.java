package io.github.maxixcom.otus.enrich.service.generator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.Optional;


@SpringBootTest(classes = IPGenerator.class)
class IPGeneratorTest {
    @SpyBean
    private IPGenerator ipGenerator;

    @Test
    void shouldGenerateValue() {
        Mockito.when(ipGenerator.getSamples()).thenReturn(List.of("0.0.0.0"));
        Mockito.when(ipGenerator.generate()).thenCallRealMethod();

        Optional<String> userAgent = ipGenerator.generate().limit(1).findFirst();
        Assertions.assertThat(userAgent)
                .isPresent()
                .containsInstanceOf(String.class)
                .hasValueSatisfying(value -> Assertions.assertThat(value).isEqualTo("0.0.0.0"));

    }

}