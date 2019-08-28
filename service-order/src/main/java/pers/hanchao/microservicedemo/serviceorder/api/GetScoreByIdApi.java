package pers.hanchao.microservicedemo.serviceorder.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.hanchao.microservicedemo.serviceorder.api.hystrix.GetScoreByIdApiHystrix;

/**
 * <p>积分服务</P>
 *
 * @author hanchao
 */
@FeignClient(value = "service-score", fallback = GetScoreByIdApiHystrix.class)
public interface GetScoreByIdApi {

    /**
     * 查询积分
     */
    @GetMapping("score/{id}")
    String getScoreById(@PathVariable("id") String id);
}
