package pers.hanchao.microservicedemo.serviceorder.command.downgrade.impl;

import lombok.extern.slf4j.Slf4j;
import pers.hanchao.microservicedemo.serviceorder.command.downgrade.AbstractDowngradeHystrixCommand;

/**
 * <p>回退降级：快速失败</P>
 *
 * @author hanchao
 */
@Slf4j
public class FailFastDowngradeHystrixCommand<T> extends AbstractDowngradeHystrixCommand<T> {
    public FailFastDowngradeHystrixCommand() {
        super();
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
        new FailFastDowngradeHystrixCommand().execute();
    }
}
