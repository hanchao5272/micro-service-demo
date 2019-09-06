package pers.hanchao.microservicedemo.serviceorder.command.downgrade.impl;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import pers.hanchao.microservicedemo.serviceorder.command.downgrade.AbstractDowngradeHystrixCommand;
import pers.hanchao.microservicedemo.serviceorder.command.util.MyHystrixCommandUtil;

/**
 * <p>回退降级：失败转移(备用服务)</P>
 *
 * @author hanchao
 */
public class FailOverDowngradHystrxCommand<T> extends AbstractDowngradeHystrixCommand<T> {

    public FailOverDowngradHystrxCommand() {
        super();
    }

    /**
     * fail-over: 失败转移：当前服务失败，则尝试转移至备用服务
     */
    @Override
    protected T getFallback() {
        return (T) new SecondLevelCacheHystrixCommand<String >().execute();
    }

    /**
     * 备用服务：通过二级缓存查询
     */
    private static class SecondLevelCacheHystrixCommand<T> extends HystrixCommand<T>{

        protected SecondLevelCacheHystrixCommand() {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(MyHystrixCommandUtil.GROUP_KEY_LEARN_HYSTRIX_COMMAND)));
        }

        @Override
        protected T run() throws Exception {
            if (true){
                System.out.println("二级缓存");
                return (T) "二级缓存";
            }else {
                throw new Exception("error");
            }
        }

        /**
         * 备用服务的回退降级策略
         */
        @Override
        protected T getFallback() {
            return null;
        }
    }

    public static void main(String[] args) {
        new FailOverDowngradHystrxCommand<String >().execute();
    }
}
