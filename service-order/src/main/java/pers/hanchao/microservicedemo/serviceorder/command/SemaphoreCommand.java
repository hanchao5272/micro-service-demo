package pers.hanchao.microservicedemo.serviceorder.command;

import com.netflix.hystrix.*;
import pers.hanchao.microservicedemo.serviceorder.service.OrderService;

/**
 * <p>信号量隔离</P>
 * 默认情况下，HystrixCommand使用线程池隔离，这种隔离适用于【依赖网络请求的服务】，因为网络请求有很多不可预知之处，这么做可以容错。
 * 如果服务不依赖于网络请求，例如获取内存缓存，则没必要采取线程池隔离，因为线程池与线程本身就是资源消耗。
 *
 * @author hanchao
 */
public class SemaphoreCommand extends HystrixCommand<String> {

    private OrderService orderService;
    private String id;

    public SemaphoreCommand(OrderService orderService, String id) {
        super(Setter
                //groupKey：服务名/业务分类名/业务组名...
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(MyHystrixCommandUtil.GROUP_KEY_LEARN_HYSTRIX_COMMAND))
                //commandKey默认由类名派生而来，可以不设置
                //.andCommandKey()
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                //执行超时时间
                                .withExecutionTimeoutInMilliseconds(5000)
                                //选择隔离策略：信号量
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                )
                //线程池隔离的区分键,可以以类名为名
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(SemaphoreCommand.class.getSimpleName()))
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                //等待线程大小
                                .withMaxQueueSize(10)
                                //核心线程大小
                                .withCoreSize(2)
                )
        );
        this.orderService = orderService;
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        return orderService.getOtherInfoByLocalCache(id);
    }
}
