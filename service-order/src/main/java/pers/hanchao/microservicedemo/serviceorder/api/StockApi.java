package pers.hanchao.microservicedemo.serviceorder.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>库存服务接口</P>
 *
 * @author hanchao
 */
@FeignClient("service-stock")
public interface StockApi {

    /**
     * 查询库存信息
     */
    @GetMapping("/stock/{id}")
    String getStockById(@PathVariable("id") String id);
}
