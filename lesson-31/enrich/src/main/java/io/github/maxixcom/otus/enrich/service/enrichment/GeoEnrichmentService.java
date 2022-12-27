package io.github.maxixcom.otus.enrich.service.enrichment;

import io.github.maxixcom.otus.enrich.domain.RichData;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Наивная имплементация IP to Geo
 */
@Service
public class GeoEnrichmentService implements EnrichmentService {
    private final static Map<String, String> ip2country = new HashMap<>();

    static {
        ip2country.put("74.197.141.235", "US");
        ip2country.put("176.106.245.30", "RU");
        ip2country.put("79.155.120.145", "ES");
        ip2country.put("151.29.15.232", "IT");
        ip2country.put("91.152.247.141", "FI");
        ip2country.put("90.191.78.8", "EE");
        ip2country.put("37.45.52.179", "BY");
        ip2country.put("5.251.120.146", "KZ");
    }

    @Override
    public RichData enrich(RichData richData) {
        return Optional.ofNullable(richData.getRequest().getIp())
                .map(ip -> richData.toBuilder()
                        .country(ip2country.getOrDefault(ip, "UNKNOWN"))
                        .build())
                .orElse(richData);
    }
}
