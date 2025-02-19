package netty.error.service.controller;

import io.micronaut.json.tree.JsonNode;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record PayloadRequest(
    String name,
    JsonNode payload
) {
}
