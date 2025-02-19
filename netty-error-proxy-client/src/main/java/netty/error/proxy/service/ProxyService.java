package netty.error.proxy.service;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.reactor.http.client.proxy.ReactorProxyHttpClient;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ProxyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyService.class);

    private static final String SCHEME = "http";
    private static final int PORT = 8081;
    private static final String LOCALHOST = "localhost";

    private final ReactorProxyHttpClient proxyClient;

    public ProxyService(
        @Client("proxy") ReactorProxyHttpClient proxyClient
    ) {
        this.proxyClient = proxyClient;
    }

    public Publisher<MutableHttpResponse<?>> proxy(final HttpRequest<?> request) {
        LOGGER.info("Proxying request to path: {}", request.getPath());

        return proxyClient.proxy(
            request.mutate()
                .uri(b -> b
                    .scheme(SCHEME)
                    .port(PORT)
                    .host(LOCALHOST)
                    .replacePath(request.getPath())));
    }
}
