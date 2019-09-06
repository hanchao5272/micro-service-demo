package pers.hanchao.microservicedemo.serviceorder.command.downgrade.impl;

import lombok.extern.slf4j.Slf4j;
import pers.hanchao.microservicedemo.serviceorder.command.downgrade.AbstractDowngradeHystrixCommand;
import pers.hanchao.microservicedemo.serviceorder.command.util.MyHystrixCommandUtil;

/**
 * <p>回退降级：快速失败</P>
 *
 * @author hanchao
 */
@Slf4j
public class FailFastDowngradeHystrixCommand<T> extends AbstractDowngradeHystrixCommand<T> {
    public FailFastDowngradeHystrixCommand(int id) {
        super(MyHystrixCommandUtil.ALWAYS_OPENED_SETTER_FOR_TEST, id);
    }

    @Override
    protected T getFallback() {
        try {
            throw new Exception("快速失败：直接抛出异常");
        } catch (Exception e) {
            log.error("快速失败：直接抛出异常");
        }
        return (T) "快速失败：直接抛出异常";
    }

    public static void main(String[] args) {
        new FailFastDowngradeHystrixCommand(1).execute();
    }
}
