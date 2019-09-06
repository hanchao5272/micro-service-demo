package pers.hanchao.microservicedemo.serviceorder.command.downgrade.impl;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import pers.hanchao.microservicedemo.serviceorder.command.util.MyHystrixCommandUtil;

import java.util.Objects;

/**
 * <p>fail-switch:失败(主动)切换</P>
 *
 * @author hanchao
 */
public class FailSwitchDowngradeHystrixCommand<T> extends HystrixCommand<T> {

    private final static DynamicBooleanProperty USE_NEW = DynamicPropertyFactory.getInstance().getBooleanProperty("USE_NEW", false);

    private Boolean useNew = null;

    public FailSwitchDowngradeHystrixCommand() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(MyHystrixCommandUtil.GROUP_KEY_LEARN_HYSTRIX_COMMAND)));
    }

    public FailSwitchDowngradeHystrixCommand(boolean useNew) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(MyHystrixCommandUtil.GROUP_KEY_LEARN_HYSTRIX_COMMAND)));
        this.useNew = useNew;
    }

    @Override
    protected T run() throws Exception {
        if (Objects.isNull(useNew)) {
            if (USE_NEW.get()) {
                return (T) new NewServiceHystrixCommand<String>().execute();
            } else {
                return (T) new OldServiceHystrixCommand<String>().execute();
            }
        } else {
            if (useNew) {
                return (T) new NewServiceHystrixCommand<String>().execute();
            } else {
                return (T) new OldServiceHystrixCommand<String>().execute();
            }
        }
    }

    @Override
    protected T getFallback() {
        return null;
    }

    private static class NewServiceHystrixCommand<T> extends HystrixCommand<T> {
        public NewServiceHystrixCommand() {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(MyHystrixCommandUtil.GROUP_KEY_LEARN_HYSTRIX_COMMAND)));
        }

        @Override
        protected T run() throws Exception {
            System.out.println("新服务信息");
            return (T) "新服务信息";
        }

        @Override
        protected T getFallback() {
            return (T) "default";
        }
    }

    private static class OldServiceHystrixCommand<T> extends HystrixCommand<T> {
        public OldServiceHystrixCommand() {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(MyHystrixCommandUtil.GROUP_KEY_LEARN_HYSTRIX_COMMAND)));
        }

        @Override
        protected T run() throws Exception {
            System.out.println("旧服务信息");
            return (T) "旧服务信息";
        }

        @Override
        protected T getFallback() {
            return (T) "default";
        }
    }

    public static void main(String[] args) {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            ConfigurationManager.getConfigInstance().setProperty("USE_NEW", true);
            new FailSwitchDowngradeHystrixCommand<String>().execute();
        } finally {
            context.shutdown();
            ConfigurationManager.getConfigInstance().clear();
        }

        context = HystrixRequestContext.initializeContext();
        try {
            ConfigurationManager.getConfigInstance().setProperty("USE_NEW", false);
            new FailSwitchDowngradeHystrixCommand<String>().execute();
        } finally {
            context.shutdown();
            ConfigurationManager.getConfigInstance().clear();
        }

        System.out.println("----------");
        new FailSwitchDowngradeHystrixCommand<String>(true).execute();
        new FailSwitchDowngradeHystrixCommand<String>(false).execute();
    }
}
