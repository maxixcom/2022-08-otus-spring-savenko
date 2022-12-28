
package io.github.maxixcom.otus.enrich.service.generator;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IPGenerator extends RandomSampleGenerator<String> {
    private final static List samples = List.of(
            "74.197.141.235", //US
            "176.106.245.30", // RU
            "79.155.120.145", // ES
            "151.29.15.232", // IT
            "91.152.247.141", //FI
            "90.191.78.8", // EE
            "37.45.52.179", // BY
            "5.251.120.146", // KZ
            "192.168.0.1"
    );

    @Override
    protected List<String> getSamples() {
        return samples;
    }
}
