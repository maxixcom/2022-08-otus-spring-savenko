package io.github.maxixcom.otus.enrich.service.generator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = UserAgentGenerator.class)
class UserAgentGeneratorTest {
    @SpyBean
    UserAgentGenerator userAgentGenerator;

    @Test
    void shouldGenerateValue() {
        Mockito.when(userAgentGenerator.getSamples()).thenReturn(List.of("User Agent"));
        Mockito.when(userAgentGenerator.generate()).thenCallRealMethod();

        Optional<String> userAgent = userAgentGenerator.generate().limit(1).findFirst();
        Assertions.assertThat(userAgent)
                .isPresent()
                .containsInstanceOf(String.class)
                .hasValueSatisfying(value -> Assertions.assertThat(value).isEqualTo("User Agent"));

    }

}