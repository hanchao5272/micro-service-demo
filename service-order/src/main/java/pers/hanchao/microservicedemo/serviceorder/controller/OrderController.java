package pers.hanchao.microservicedemo.serviceorder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>订单服务</P>
 *
 * @author hanchao
 */
@RestController
public class OrderController {
    /**
     * 查询订单
     */
    @GetMapping("/order/{id}")
    public String getOrderById(@PathVariable("id") String id) {
        return String.format("%s's Order Info. ", id);
    }
}
