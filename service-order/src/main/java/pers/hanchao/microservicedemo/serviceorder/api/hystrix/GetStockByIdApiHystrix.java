package pers.hanchao.microservicedemo.serviceorder.api.hystrix;

import org.springframework.stereotype.Component;
import pers.hanchao.microservicedemo.serviceorder.api.GetStockByIdApi;

/**
 * <p>获取库存服务熔断器</P>
 *
 * @author hanchao
 */
@Component
public class GetStockByIdApiHystrix implements GetStockByIdApi {
    /**
     * 查询库存信息
     *
     * @param id id
     */
    @Override
    public String getStockById(String id) {
        return "库存信息[服务异常,已熔断]";
    }
}
