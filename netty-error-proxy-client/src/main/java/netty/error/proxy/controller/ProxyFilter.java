package netty.error.proxy.controller;

import io.micronaut.core.order.Ordered;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import netty.error.proxy.service.ProxyService;
import netty.error.proxy.util.UriUtils;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@Filter("/**")
public class ProxyFilter implements HttpServerFilter {
    private final ProxyService proxyService;

    public ProxyFilter(final ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    // We want to run last
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        final String path = UriUtils.validatePath(request);

        final Publisher<MutableHttpResponse<?>> response;
        if (path.isEmpty() || path.equals("/")) {
            response = Flux.just(HttpResponse.ok("Service UP!"));
        } else {
            response = proxyService.proxy(request);
        }

        return response;
    }
}
