package pers.hanchao.microservicedemo.serviceorder.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pers.hanchao.microservicedemo.serviceorder.api.GetScoreByIdApi;
import pers.hanchao.microservicedemo.serviceorder.api.GetStockByIdApi;
import pers.hanchao.microservicedemo.serviceorder.command.semaphore.SemaphoreCommand;
import pers.hanchao.microservicedemo.serviceorder.command.simple.SimpleCommand;
import pers.hanchao.microservicedemo.serviceorder.service.OrderService;

import javax.annotation.Resource;

/**
 * <p>订单服务</P>
 *
 * @author hanchao
 */
@RefreshScope
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private GetScoreByIdApi getScoreByIdApi;

    @Resource
    private GetStockByIdApi getStockByIdApi;

    @Value("${server.port}")
    private Integer port;

    @Value("${service-order.downgrade-switch.service-score}")
    private Boolean serviceScoreSwitch;

    /**
     * 查询订单
     */
    @GetMapping("/order/{id}")
    public String getOrderById(@PathVariable("id") String id) {
        //获取库存信息
        String stockInfo = getStockByIdApi.getStockById(id);

        //获取积分信息
        String scoreInfo;
        if (!serviceScoreSwitch) {
            scoreInfo = getScoreByIdApi.getScoreById(id);
        } else {
            scoreInfo = String.format("积分信息[id=%s,port=降级] ", id);
        }

        //获取其他服务
        String otherInfo = new SimpleCommand(orderService, id).execute();

        //获取本地缓存
        String localCache = new SemaphoreCommand(orderService,id).execute();

        return String.format("订单信息{\nid=%s,\nport=%d,\nstock=%s,\nscore=%s,\nother=%s,\nlocal-cache=%s\n} ", id, port, stockInfo, scoreInfo, otherInfo,localCache);
    }
}
