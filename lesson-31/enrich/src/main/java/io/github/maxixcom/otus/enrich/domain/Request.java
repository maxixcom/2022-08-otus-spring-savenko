package io.github.maxixcom.otus.enrich.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Request {
    private final String ip;
    private final String userAgent;
}
