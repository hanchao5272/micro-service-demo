package pers.hanchao.microservicedemo.serviceorder.command;

import com.netflix.hystrix.*;
import pers.hanchao.microservicedemo.serviceorder.service.OrderService;

/**
 * <p></P>
 *
 * @author hanchao
 */
public class SimpleCommand extends HystrixCommand<String> {

    private OrderService orderService;
    private String id;

    public SimpleCommand(OrderService orderService, String id) {
        super(Setter
                //groupKey 命令分组名，可以是一个项目一个groupKey，也可以是一类业务一个groupKey，一个开发组也可以是一个groupKey
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("learnToUseHystrixCommand"))
                //commandKey 命令名，一般从类名派生而来
                .andCommandKey(HystrixCommandKey.Factory.asKey("simpleCommand"))
                //hystrixCommand的配置
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                //executionTimeoutInMilliseconds 线程池中的线程超时毫秒
                                .withExecutionTimeoutInMilliseconds(5000)
                )
                //threadPoolKey 线程池名，线程池隔离技术中区分不同线程池的关键字
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("simpleCommand"))
                //线程池的配置
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                //等待队列的最大容量
                                .withMaxQueueSize(10)
                                //核心线程池大小
                                .withCoreSize(2)
                )
        );
        this.orderService = orderService;
        this.id = id;
    }

    /**
     * 正常的调用方法
     * <p>
     * 当执行{@link #execute()} or {@link #queue()}方法时，都会执行
     */
    @Override
    protected String run() {
        return orderService.getOtherInfo("simpleCommand",id);
    }
}