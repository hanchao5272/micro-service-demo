package pers.hanchao.microservicedemo.serviceorder.command.downgrade.impl;

import pers.hanchao.microservicedemo.serviceorder.command.downgrade.AbstractDowngradeHystrixCommand;

import java.util.List;

/**
 * <p>回退降级：无声失败</P>
 *
 * @author hanchao
 */
public class FailSilentDowngradeHystrixCommand<T> extends AbstractDowngradeHystrixCommand<T> {

    public FailSilentDowngradeHystrixCommand() {
        super();
    }

    /**
     * fail-silent：无声失败，返回空、空对象等
     *
     * fail-silent也可称之为fail-safe,即：失败安全.
     *
     * 这种策略适用于：不太重要的业务，例如写日志等。
     */
    @Override
    protected T getFallback() {
//        return Collections.EMPTY_LIST;
//        return Collections.EMPTY_MAP;
//        return Collections.EMPTY_SET;
        System.out.println("null");
        return null;
    }

    public static void main(String[] args) {
        new FailSilentDowngradeHystrixCommand<List<String>>().execute();
    }
}
