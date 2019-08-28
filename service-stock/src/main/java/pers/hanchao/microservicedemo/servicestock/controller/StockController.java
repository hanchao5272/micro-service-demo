package pers.hanchao.microservicedemo.servicestock.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>库存服务</P>
 *
 * @author hanchao
 */
@RestController
public class StockController {
    @Value("${server.port}")
    private Integer port;

    /**
     * 查询库存
     */
    @GetMapping("/stock/{id}")
    public String getStockById(@PathVariable("id") String id) {
        return String.format("库存信息[id=%s,port=%d] ", id, port);
    }
}
