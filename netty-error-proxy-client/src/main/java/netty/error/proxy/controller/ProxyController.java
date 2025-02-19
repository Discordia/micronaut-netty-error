package netty.error.proxy.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.exceptions.HttpStatusException;
import reactor.core.publisher.Mono;

@Controller("/")
public class ProxyController {

    @Get(value = "/{+path}", consumes = MediaType.ALL, produces = MediaType.APPLICATION_JSON)
    public Mono<? extends HttpResponse<?>> get(String path) {
        return Mono.error(ProxyController::createForbidden);
    }

    @Post(value = "/{+path}", consumes = MediaType.ALL, produces = MediaType.APPLICATION_JSON)
    public Mono<? extends HttpResponse<?>> post(String path) {
        return Mono.error(ProxyController::createForbidden);
    }

    @Put(value = "/{+path}", consumes = MediaType.ALL, produces = MediaType.APPLICATION_JSON)
    public Mono<? extends HttpResponse<?>> put(String path) {
        return Mono.error(ProxyController::createForbidden);
    }

    @Delete(value = "/{+path}", consumes = MediaType.ALL, produces = MediaType.APPLICATION_JSON)
    public Mono<? extends HttpResponse<?>> delete(String path) {
        return Mono.error(ProxyController::createForbidden);
    }

    private static HttpStatusException createForbidden() {
        return new HttpStatusException(HttpStatus.FORBIDDEN, new ErrorBody("ProxyFailed", "Failed to proxy request"));
    }
}
