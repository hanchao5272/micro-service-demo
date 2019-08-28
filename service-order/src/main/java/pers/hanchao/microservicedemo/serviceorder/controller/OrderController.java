package pers.hanchao.microservicedemo.serviceorder.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pers.hanchao.microservicedemo.serviceorder.api.ScoreApi;
import pers.hanchao.microservicedemo.serviceorder.api.StockApi;

import javax.annotation.Resource;

/**
 * <p>订单服务</P>
 *
 * @author hanchao
 */
@RestController
public class OrderController {
    @Resource
    private ScoreApi scoreApi;

    @Resource
    private StockApi stockApi;

    @Value("${server.port}")
    private Integer port;

    /**
     * 查询订单
     * 1.如果stock和score只有一个节点，则两者无需添加Load balancer，也就是Feign
     */
    @GetMapping("/order/{id}")
    public String getOrderById(@PathVariable("id") String id) {
        //获取库存信息
        String stockInfo = stockApi.getStockById(id);

        //获取积分信息
        String scoreInfo = scoreApi.getScoreById(id);

        return String.format("订单信息{id=%s,port=%d,stock=%s,score=%s} ", id, port, stockInfo, scoreInfo);
    }
}
