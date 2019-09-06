package pers.hanchao.microservicedemo.serviceorder.command.downgrade;

import com.netflix.hystrix.HystrixCommand;

/**
 * <p>回退降级</P>
 *
 * @author hanchao
 */
public abstract class AbstractDowngradeHystrixCommand extends HystrixCommand<String> {

    private int id;

    public AbstractDowngradeHystrixCommand(Setter setter, int id) {
        super(setter);
        this.id = id;
    }

    /**
     * 正常业务逻辑
     */
    @Override
    protected String run() throws Exception{
        return String.format("回退降级[%d]", id);
    }
}
