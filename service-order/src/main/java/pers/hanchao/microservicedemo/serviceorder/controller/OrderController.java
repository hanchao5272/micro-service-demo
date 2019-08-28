package pers.hanchao.microservicedemo.serviceorder.controller;

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

    /**
     * 查询订单
     */
    @GetMapping("/order/{id}")
    public String getOrderById(@PathVariable("id") String id) {
        //获取库存信息
        String stockInfo = stockApi.getStockById(id);

        //获取积分信息
        String scoreInfo = scoreApi.getScoreById(id);

        return String.format("%s's Order Info : stock = 「%s」, score = 「%s」 ", id, stockInfo, scoreInfo);
    }
}
