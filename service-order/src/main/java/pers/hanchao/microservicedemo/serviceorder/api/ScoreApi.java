package pers.hanchao.microservicedemo.serviceorder.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>积分服务</P>
 *
 * @author hanchao
 */
@FeignClient("service-score")
public interface ScoreApi {

    /**
     * 查询积分
     */
    @GetMapping("score/{id}")
    String getScoreById(@PathVariable("id") String id);
}
