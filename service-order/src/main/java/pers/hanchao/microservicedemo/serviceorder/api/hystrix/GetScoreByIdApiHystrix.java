package pers.hanchao.microservicedemo.serviceorder.api.hystrix;

import org.springframework.stereotype.Component;
import pers.hanchao.microservicedemo.serviceorder.api.GetScoreByIdApi;

/**
 * <p>获取积分服务熔断器</P>
 *
 * @author hanchao
 */
@Component
public class GetScoreByIdApiHystrix implements GetScoreByIdApi {
    /**
     * 查询积分
     *
     * @param id id
     */
    @Override
    public String getScoreById(String id) {
        return "积分信息[服务异常,已熔断]";
    }
}
