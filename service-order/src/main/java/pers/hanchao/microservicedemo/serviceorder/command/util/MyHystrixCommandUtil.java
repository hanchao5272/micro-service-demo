package pers.hanchao.microservicedemo.serviceorder.command.util;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * <p>熔断命令工具类</P>
 *
 * @author hanchao
 */
public class MyHystrixCommandUtil {
    public static final String GROUP_KEY_LEARN_HYSTRIX_COMMAND = "learnToUseHystrixCommand";
    public static final HystrixCommand.Setter ALWAYS_OPENED_SETTER_FOR_TEST = HystrixCommand.Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey(GROUP_KEY_LEARN_HYSTRIX_COMMAND))
            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerForceOpen(true));
}
