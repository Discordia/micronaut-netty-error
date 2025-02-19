package netty.error.service.controller;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import static io.micronaut.http.HttpStatus.ACCEPTED;

@Controller("/service")
public class ServiceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceController.class);

    @Post("/payload")
    @Status(ACCEPTED)
    public Mono<Void> receivePayload(@Body PayloadRequest request) {
        return Mono.just(request)
            .map(this::simulateHandlingOfResponseTakesTime)
            .then();
    }

    private PayloadRequest simulateHandlingOfResponseTakesTime(PayloadRequest payloadRequest) {
        LOGGER.info("Handling request");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }

        LOGGER.info("Finished handling request: {}", payloadRequest);

        return payloadRequest;
    }
}
