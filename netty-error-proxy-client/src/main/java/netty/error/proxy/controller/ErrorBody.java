package netty.error.proxy.controller;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ErrorBody(
    String errorType,
    String errorMessage
) {
}
