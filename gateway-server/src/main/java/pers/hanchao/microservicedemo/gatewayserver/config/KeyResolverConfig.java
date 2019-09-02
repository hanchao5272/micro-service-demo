package pers.hanchao.microservicedemo.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.hanchao.microservicedemo.gatewayserver.config.resolver.HostAddressKeyResolver;
import pers.hanchao.microservicedemo.gatewayserver.config.resolver.UidKeyResolver;
import pers.hanchao.microservicedemo.gatewayserver.config.resolver.UriPathKeyResolver;

/**
 * <p></P>
 * https://blog.csdn.net/forezp/article/details/85081162
 *
 * @author hanchao
 */
@Configuration
public class KeyResolverConfig {

    /**
     * 根据IP和Port限流
     */
    @Bean
    public HostAddressKeyResolver hostAddressKeyResolver() {
        return new HostAddressKeyResolver();
    }

    /**
     * 根据URI限流
     */
    @Bean
    public UriPathKeyResolver uriPathKeyResolver(){
        return new UriPathKeyResolver();
    }

    /**
     * 根据uid限流
     */
    @Bean
    public UidKeyResolver uidKeyResolver(){
        return new UidKeyResolver();
    }
}
