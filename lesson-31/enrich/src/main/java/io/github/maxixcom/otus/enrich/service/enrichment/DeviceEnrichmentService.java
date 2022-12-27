package io.github.maxixcom.otus.enrich.service.enrichment;

import eu.bitwalker.useragentutils.UserAgent;
import io.github.maxixcom.otus.enrich.domain.RichData;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceEnrichmentService implements EnrichmentService {
    @Override
    public RichData enrich(RichData richData) {
        RichData.RichDataBuilder builder = richData.toBuilder();
        UserAgent userAgent = UserAgent.parseUserAgentString(richData.getRequest().getUserAgent());

        Optional.ofNullable(userAgent.getOperatingSystem()).ifPresent(operatingSystem ->
                builder
                        .deviceType(operatingSystem.getDeviceType().getName())
                        .osGroup(operatingSystem.getGroup().getName())
                        .osName(operatingSystem.getName())
                        .osManufacture(operatingSystem.getManufacturer().getName()));

        Optional.ofNullable(userAgent.getBrowser()).ifPresent(browser ->
                builder
                        .browserGroup(browser.getGroup().getGroup().getName())
                        .browserName(browser.getName()));

        Optional.ofNullable(userAgent.getBrowserVersion()).ifPresent(version ->
                builder.browserVersion(version.getVersion())
        );

        return builder.build();
    }
}
