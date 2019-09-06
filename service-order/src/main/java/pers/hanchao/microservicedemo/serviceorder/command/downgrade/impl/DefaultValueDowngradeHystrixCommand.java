package pers.hanchao.microservicedemo.serviceorder.command.downgrade.impl;

import com.google.common.collect.Lists;
import pers.hanchao.microservicedemo.serviceorder.command.downgrade.AbstractDowngradeHystrixCommand;

import java.util.List;

/**
 * <p>fail default: 失败托底</P>
 *
 * @author hanchao
 */
public class DefaultValueDowngradeHystrixCommand<T> extends AbstractDowngradeHystrixCommand<T> {
    public DefaultValueDowngradeHystrixCommand() {
        super();
    }

    /**
     * fail-default: 失败托底：提前准备一个默认值作为托底数据，当失败时，返回这个默认值
     */
    @Override
    protected T getFallback() {
        List<Integer> defaultList = Lists.newArrayList(1,2,3);
        defaultList.forEach(System.out::println);
        return (T)defaultList;
    }

    public static void main(String[] args) {
        new DefaultValueDowngradeHystrixCommand<List<Integer>>().execute();
    }
}
