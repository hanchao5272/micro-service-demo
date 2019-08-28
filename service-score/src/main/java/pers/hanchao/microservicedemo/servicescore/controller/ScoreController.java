package pers.hanchao.microservicedemo.servicescore.controller;

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

    /**
     * 查询积分
     */
    @GetMapping("/score/{id}")
    public String getScoreById(@PathVariable("id") String id) {
        return String.format("%s's Score Info. ", id);
    }
}
