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
    public String getOtherInfoByNetWork(String threadPoolKey, String id) {
        return String.format("其他信息(通过网络请求获取)[id=%s,hystrixCommand=线程池隔离] ", id, threadPoolKey);
    }

    @Override
    public String getOtherInfoByLocalCache(String id) {
        return String.format("其他信息(通过本地缓存获取)[id=%s,hystrixCommand=信号量隔离]", id);
    }
}
