package pers.hanchao.microservicedemo.serviceorder.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.hanchao.microservicedemo.serviceorder.api.hystrix.GetStockByIdApiHystrix;

/**
 * <p>库存服务接口</P>
 *
 * @author hanchao
 */
@FeignClient(value = "service-stock",fallback = GetStockByIdApiHystrix.class)
public interface GetStockByIdApi {

    /**
     * 查询库存信息
     */
    @GetMapping("/stock/{id}")
    String getStockById(@PathVariable("id") String id);
}
