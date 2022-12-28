

package io.github.maxixcom.otus.enrich.service.generator;

import io.github.maxixcom.otus.enrich.domain.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestGenerator extends RandomSampleGenerator<Request> {
    private final IPGenerator ipGenerator;
    private final UserAgentGenerator userAgentGenerator;

    @Override
    protected List<Request> getSamples() {
        return userAgentGenerator.generate()
                .limit(20)
                .flatMap(userAgent ->
                        ipGenerator.generate()
                                .limit(10)
                                .map(ip -> new Request(ip, userAgent)))
                .collect(Collectors.toList());
    }

}
