package pers.hanchao.microservicedemo.serviceorder.service;

/**
 * <p></P>
 *
 * @author hanchao
 */
public interface OrderService {

    String getOtherInfoByNetWork(String threadPoolKey, String id);

    String getOtherInfoByLocalCache(String id);
}
