package io.github.maxixcom.otus.enrich.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Builder(toBuilder = true)
@Getter
@ToString
public class RichData {
    private final Request request;
    private final String country;
    private final String deviceType;
    private final String osName;
    private final String osManufacture;
    private final String osGroup;
    private final String browserName;
    private final String browserGroup;
    private final String browserVersion;

    public static RichData fromRequest(Request request) {
        return RichData.builder()
                .request(request)
                .build();
    }
}
