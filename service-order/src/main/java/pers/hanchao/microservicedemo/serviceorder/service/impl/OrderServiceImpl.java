package pers.hanchao.microservicedemo.serviceorder.service.impl;

import org.springframework.stereotype.Service;
import pers.hanchao.microservicedemo.serviceorder.service.OrderService;

/**
 * <p></P>
 *
 * @author hanchao
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public String getOtherInfo(String threadPoolKey, String id) {
        return String.format("其他信息(通过网络请求获取)[id=%s,threadPoolKey=%s] ", id, threadPoolKey);
    }
}
