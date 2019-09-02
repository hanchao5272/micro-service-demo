package pers.hanchao.microservicedemo.gatewayserver.config.resolver;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * <p>根据Uid去限流</P>
 *
 * @author hanchao
 */
public class UidKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(Objects.requireNonNull(exchange.getRequest().getQueryParams().getFirst("uid")));
    }
}
