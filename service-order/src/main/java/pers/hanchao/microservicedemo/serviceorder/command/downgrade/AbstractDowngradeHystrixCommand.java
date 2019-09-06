package pers.hanchao.microservicedemo.serviceorder.command.downgrade;

import com.netflix.hystrix.HystrixCommand;
import pers.hanchao.microservicedemo.serviceorder.command.util.MyHystrixCommandUtil;

/**
 * <p>回退降级</P>
 *
 * @author hanchao
 */
public abstract class AbstractDowngradeHystrixCommand<T> extends HystrixCommand<T> {

    /**
     * 为了方便测试，断路器设置成始终开启
     */
    public AbstractDowngradeHystrixCommand() {
        super(MyHystrixCommandUtil.ALWAYS_OPENED_SETTER_FOR_TEST);
    }

    /**
     * 正常业务逻辑
     */
    @Override
    protected T run() throws Exception {
        return (T) "回退降级";
    }
}
