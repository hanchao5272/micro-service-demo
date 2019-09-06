package pers.hanchao.microservicedemo.serviceorder.command.breaker;

import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import pers.hanchao.microservicedemo.serviceorder.command.util.MyHystrixCommandUtil;

/**
 * <p>CircuitBreaker:断路器</P>
 *
 * @author hanchao
 */
@Slf4j
public class CircuitBreakerHystrixCommand extends HystrixCommand<String> {
    private String id;

    public CircuitBreakerHystrixCommand(String id) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(MyHystrixCommandUtil.GROUP_KEY_LEARN_HYSTRIX_COMMAND))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                //使用断路器
                                .withCircuitBreakerEnabled(true)
                                //强制断路器始终是开启状态，默认false；开启之后，断路，请求直接失败
                                .withCircuitBreakerForceOpen(false)
                                //强制断路器始终是关闭状态，默认false；关闭之后，不断路，请求继续执行
                                .withCircuitBreakerForceClosed(false)
                                //断路器开启条件一：请求容量。错误百分比的前置条件，在10秒，至少有20个请求，才开始计算失败百分比。
                                .withCircuitBreakerRequestVolumeThreshold(10)
                                //断路器开启条件二：错误百分比。默认50%。
                                .withCircuitBreakerErrorThresholdPercentage(50)
                                //断路器开启之后的休眠窗口时间，默认5秒。当前断路器开启之后，经过5秒之后，会尝试半开状态，尝试进行部分放量进行试探，以确定服务是否恢复。
                                .withCircuitBreakerSleepWindowInMilliseconds(5000)
                )
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(CircuitBreakerHystrixCommand.class.getSimpleName()))
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withMaxQueueSize(10)
                                .withCoreSize(2)
                )
        );
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        //50%成功率
        if (RandomUtils.nextBoolean()){
            log.info("id={} Execute Success",id);
            return "Execute Success";
        }else {
            throw new Exception("Execute Failed");
        }
    }

    /**
     * 断路器开启时，执行的失败处理方案
     */
    @Override
    protected String getFallback() {
        log.info("id={} Execute Failed, FallBack",id);
        return "Execute Failed, FallBack";
    }

    /**
     * 测试断路器
     */
    public static void main(String[] args) throws InterruptedException {
        /**
         * 13:02:22.091 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=0 Execute Failed, FallBack
         * 13:02:22.600 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=1 Execute Failed, FallBack
         * 13:02:23.105 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=2 Execute Success
         * 13:02:23.613 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=3 Execute Failed, FallBack
         * 13:02:24.120 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=4 Execute Failed, FallBack
         * 13:02:24.624 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=5 Execute Success
         * 13:02:25.133 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=6 Execute Failed, FallBack
         * 13:02:25.636 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=7 Execute Failed, FallBack
         * 13:02:26.144 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=8 Execute Failed, FallBack
         * 13:02:26.651 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=9 Execute Failed, FallBack
         * //后续日志由main线程打印，说明断路器已开启，直接FallBack
         * 13:02:27.158 [main] INFO  - id=10 Execute Failed, FallBack
         * 13:02:27.662 [main] INFO  - id=11 Execute Failed, FallBack
         * 13:02:28.167 [main] INFO  - id=12 Execute Failed, FallBack
         * 13:02:28.672 [main] INFO  - id=13 Execute Failed, FallBack
         * 13:02:29.177 [main] INFO  - id=14 Execute Failed, FallBack
         * 13:02:29.683 [main] INFO  - id=15 Execute Failed, FallBack
         * 13:02:30.189 [main] INFO  - id=16 Execute Failed, FallBack
         * 13:02:30.693 [main] INFO  - id=17 Execute Failed, FallBack
         * 13:02:31.199 [main] INFO  - id=18 Execute Failed, FallBack
         * 13:02:31.705 [main] INFO  - id=19 Execute Failed, FallBack
         * //从断路器开启到此，经过了5秒钟，日志重新变为由CircuitBreakerHystrixCommand打印，表示进入半开状态
         * 13:02:32.210 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=20 Execute Success
         * 13:02:32.728 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=21 Execute Success
         * 13:02:33.236 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=22 Execute Failed, FallBack
         * 13:02:33.742 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=23 Execute Failed, FallBack
         * 13:02:34.247 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=24 Execute Failed, FallBack
         * 13:02:34.750 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=25 Execute Success
         * 13:02:35.255 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=26 Execute Success
         * 13:02:35.760 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=27 Execute Success
         * 13:02:36.266 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=28 Execute Success
         * 13:02:36.773 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=29 Execute Failed, FallBack
         * 13:02:37.278 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=30 Execute Failed, FallBack
         * 13:02:37.782 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=31 Execute Success
         * 13:02:38.288 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=32 Execute Success
         * 13:02:38.794 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=33 Execute Success
         * 13:02:39.300 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=34 Execute Failed, FallBack
         * 13:02:39.808 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=35 Execute Failed, FallBack
         * 13:02:40.312 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=36 Execute Success
         * 13:02:40.816 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=37 Execute Success
         * 13:02:41.322 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=38 Execute Success
         * 13:02:41.833 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=39 Execute Failed, FallBack
         * 13:02:42.338 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=40 Execute Success
         * 13:02:42.843 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=41 Execute Success
         * 13:02:43.349 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=42 Execute Failed, FallBack
         * 13:02:43.856 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=43 Execute Failed, FallBack
         * 13:02:44.362 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=44 Execute Success
         * 13:02:44.867 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=45 Execute Success
         * 13:02:45.372 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=46 Execute Failed, FallBack
         * 13:02:45.878 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=47 Execute Failed, FallBack
         * 13:02:46.384 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=48 Execute Success
         * 13:02:46.889 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=49 Execute Success
         * 13:02:47.392 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=50 Execute Success
         * 13:02:47.899 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=51 Execute Failed, FallBack
         * 13:02:48.404 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=52 Execute Success
         * 13:02:48.909 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=53 Execute Failed, FallBack
         * 13:02:49.415 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=54 Execute Success
         * 13:02:49.921 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=55 Execute Failed, FallBack
         * 13:02:50.428 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=56 Execute Failed, FallBack
         * 13:02:50.933 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=57 Execute Failed, FallBack
         * //上述从半开到此持续了18s，说明断路器由半开转为了关闭状态
         * //后续日志由main线程打印，说明断路器再次开启，直接FallBack
         * 13:02:51.439 [main] INFO  - id=58 Execute Failed, FallBack
         * 13:02:51.945 [main] INFO  - id=59 Execute Failed, FallBack
         * 13:02:52.449 [main] INFO  - id=60 Execute Failed, FallBack
         * 13:02:52.955 [main] INFO  - id=61 Execute Failed, FallBack
         * 13:02:53.460 [main] INFO  - id=62 Execute Failed, FallBack
         * 13:02:53.966 [main] INFO  - id=63 Execute Failed, FallBack
         * 13:02:54.471 [main] INFO  - id=64 Execute Failed, FallBack
         * 13:02:54.975 [main] INFO  - id=65 Execute Failed, FallBack
         * 13:02:55.480 [main] INFO  - id=66 Execute Failed, FallBack
         * 13:02:55.985 [main] INFO  - id=67 Execute Failed, FallBack
         * //从断路器再次开启到此，经过了5秒钟，日志重新变为由CircuitBreakerHystrixCommand打印，表示进入半开状态
         * 13:02:56.489 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=68 Execute Success
         * 13:02:56.997 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=69 Execute Failed, FallBack
         * 13:02:57.504 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=70 Execute Failed, FallBack
         * 13:02:58.011 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=71 Execute Failed, FallBack
         * 13:02:58.516 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=72 Execute Success
         * 13:02:59.020 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=73 Execute Failed, FallBack
         * 13:02:59.525 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=74 Execute Failed, FallBack
         * 13:03:00.029 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=75 Execute Success
         * 13:03:00.533 [hystrix-CircuitBreakerHystrixCommand-1] INFO  - id=76 Execute Success
         * 13:03:01.036 [hystrix-CircuitBreakerHystrixCommand-2] INFO  - id=77 Execute Success
         * //上述从半开到此持续了5s，说明断路器由半开转为了开启状态，后续日志也说明此问题
         * 13:03:01.539 [main] INFO  - id=78 Execute Failed, FallBack
         * 13:03:02.044 [main] INFO  - id=79 Execute Failed, FallBack
         */
//        //1s内2个请求，10秒内20个请求，满足请求容量条件
//        for (int i = 0; i < 80; i++) {
//            //1s两个请求
//            Thread.sleep(500);
//            new CircuitBreakerHystrixCommand(String.valueOf(i)).execute();
//        }

        /**
         * //下列日志都是有CircuitBreakerHystrixCommand线程打印的，说明断路器一直都未开启
         * //因为不满足条件1：withCircuitBreakerRequestVolumeThreshold(10)：10s种内有10个请求
         * 13:08:49.217 [hystrix-CircuitBreakerHystrixCommand-1] - id=0 Execute Failed, FallBack
         * 13:08:51.224 [hystrix-CircuitBreakerHystrixCommand-2] - id=1 Execute Success
         * 13:08:53.231 [hystrix-CircuitBreakerHystrixCommand-1] - id=2 Execute Failed, FallBack
         * 13:08:55.239 [hystrix-CircuitBreakerHystrixCommand-2] - id=3 Execute Failed, FallBack
         * 13:08:57.245 [hystrix-CircuitBreakerHystrixCommand-1] - id=4 Execute Failed, FallBack
         * 13:08:59.250 [hystrix-CircuitBreakerHystrixCommand-2] - id=5 Execute Success
         * 13:09:01.259 [hystrix-CircuitBreakerHystrixCommand-1] - id=6 Execute Failed, FallBack
         * 13:09:03.265 [hystrix-CircuitBreakerHystrixCommand-2] - id=7 Execute Failed, FallBack
         * 13:09:05.269 [hystrix-CircuitBreakerHystrixCommand-1] - id=8 Execute Success
         * 13:09:07.276 [hystrix-CircuitBreakerHystrixCommand-2] - id=9 Execute Failed, FallBack
         * 13:09:09.282 [hystrix-CircuitBreakerHystrixCommand-1] - id=10 Execute Success
         * 13:09:11.287 [hystrix-CircuitBreakerHystrixCommand-2] - id=11 Execute Failed, FallBack
         * 13:09:13.289 [hystrix-CircuitBreakerHystrixCommand-1] - id=12 Execute Success
         * 13:09:15.293 [hystrix-CircuitBreakerHystrixCommand-2] - id=13 Execute Success
         * 13:09:17.297 [hystrix-CircuitBreakerHystrixCommand-1] - id=14 Execute Failed, FallBack
         * 13:09:19.304 [hystrix-CircuitBreakerHystrixCommand-2] - id=15 Execute Failed, FallBack
         * 13:09:21.307 [hystrix-CircuitBreakerHystrixCommand-1] - id=16 Execute Success
         * 13:09:23.313 [hystrix-CircuitBreakerHystrixCommand-2] - id=17 Execute Failed, FallBack
         * 13:09:25.316 [hystrix-CircuitBreakerHystrixCommand-1] - id=18 Execute Success
         * 13:09:27.323 [hystrix-CircuitBreakerHystrixCommand-2] - id=19 Execute Failed, FallBack
         * 13:09:29.328 [hystrix-CircuitBreakerHystrixCommand-1] - id=20 Execute Success
         * 13:09:31.332 [hystrix-CircuitBreakerHystrixCommand-2] - id=21 Execute Success
         * 13:09:33.337 [hystrix-CircuitBreakerHystrixCommand-1] - id=22 Execute Success
         * 13:09:35.342 [hystrix-CircuitBreakerHystrixCommand-2] - id=23 Execute Success
         * 13:09:37.346 [hystrix-CircuitBreakerHystrixCommand-1] - id=24 Execute Failed, FallBack
         * 13:09:39.351 [hystrix-CircuitBreakerHystrixCommand-2] - id=25 Execute Failed, FallBack
         * 13:09:41.356 [hystrix-CircuitBreakerHystrixCommand-1] - id=26 Execute Success
         * 13:09:43.363 [hystrix-CircuitBreakerHystrixCommand-2] - id=27 Execute Failed, FallBack
         * 13:09:45.366 [hystrix-CircuitBreakerHystrixCommand-1] - id=28 Execute Success
         * 13:09:47.374 [hystrix-CircuitBreakerHystrixCommand-2] - id=29 Execute Failed, FallBack
         * 13:09:49.380 [hystrix-CircuitBreakerHystrixCommand-1] - id=30 Execute Success
         * 13:09:51.385 [hystrix-CircuitBreakerHystrixCommand-2] - id=31 Execute Failed, FallBack
         * 13:09:53.389 [hystrix-CircuitBreakerHystrixCommand-1] - id=32 Execute Success
         * 13:09:55.393 [hystrix-CircuitBreakerHystrixCommand-2] - id=33 Execute Failed, FallBack
         * 13:09:57.395 [hystrix-CircuitBreakerHystrixCommand-1] - id=34 Execute Success
         * 13:09:59.398 [hystrix-CircuitBreakerHystrixCommand-2] - id=35 Execute Failed, FallBack
         * 13:10:01.402 [hystrix-CircuitBreakerHystrixCommand-1] - id=36 Execute Success
         * 13:10:03.405 [hystrix-CircuitBreakerHystrixCommand-2] - id=37 Execute Success
         * 13:10:05.412 [hystrix-CircuitBreakerHystrixCommand-1] - id=38 Execute Failed, FallBack
         * 13:10:07.417 [hystrix-CircuitBreakerHystrixCommand-2] - id=39 Execute Failed, FallBack
         */
        //2s内1个请求，10秒内5个请求，满足请求容量条件
        for (int i = 0; i < 40; i++) {
            //2s一个请求
            Thread.sleep(2000);
            new CircuitBreakerHystrixCommand(String.valueOf(i)).execute();
        }


    }
}
