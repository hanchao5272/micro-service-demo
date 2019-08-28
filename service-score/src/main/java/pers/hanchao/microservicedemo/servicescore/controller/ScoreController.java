package pers.hanchao.microservicedemo.servicescore.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>积分服务</P>
 *
 * @author hanchao
 */
@RestController
public class ScoreController {
    @Value("${server.port}")
    private Integer port;

    /**
     * 查询积分
     */
    @GetMapping("/score/{id}")
    public String getScoreById(@PathVariable("id") String id) {
        return String.format("积分信息[id=%s,port=%d] ", id, port);
    }
}
