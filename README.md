## 服务清单
| 服务 | 描述 | 起始端口号 | 备注 |
| ---- | ---------- | ---- | ---- |
| zipkin-server | 链路追踪中心 | 9494 |  |
| eureka-server | 服务注册中心 | 9090 |  |
| gateway-server | 网关 | 8080 |  |
| config-server | 配置管理中心 | 7070 |  |
| service-order | 订单服务 | 6060 |  |
| service-stock | 库存服务 | 5050 |  |
| service-score | 积分服务 | 4040 |  |


## 关于zipkin-server

- 下载地址： [https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/](https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/)
  ```jshelllanguage
  admindeMacBook-Pro:Downloads admin$ pwd
  /Users/admin/Downloads
  admindeMacBook-Pro:Downloads admin$ ls zipkin-server-2.10.1-exec.jar
  zipkin-server-2.10.1-exec.jar
  ```
- 运行命令： `java -jar zipkin-server-2.10.1-exec.jar`
- 访问地址： http://localhost:9411/zipkin/

## 关于config server
- 要开启手动刷新，需要引入jar包：actuator ['æktʃʊeɪtə] 执行者
- 要开启手动刷新，需要在引用配置的相关类上，添加注解`@RefreshScope`, @see org.springframework.cloud.context.config.annotation.RefreshScope
- http://127.0.0.1:7071/service-order.downgrade-switch.service-score/dev GET 配置相关信息：
  ```jshelllanguage
    // 20190903125845
    // http://127.0.0.1:7071/service-order.downgrade-switch.service-score/dev
    
    {
      "name": "service-order.downgrade-switch.service-score",
      "profiles": [
        "dev"
      ],
      "label": null,
      "version": "0eaffa8846b866124a526bda8efe337d8c5b5da6",
      "state": null,
      "propertySources": [
        
      ]
    }
  ```
- 修改git配置并push
- 刷新配置

配置刷新的三种方式：
### 重启config-server服务
### 手动刷新
- 添加配置`management.endpoints.web.exposure.include= *`
- 刷新指定服务: `curl -XPOST http://localhost:6061/actuator/refresh` 
- 从新调用服务：`http://localhost:8082/order/1`，有如下表现
    ```jshelllanguage
    订单信息{id=1,port=6062,stock=库存信息[服务异常,已熔断],score=积分信息[服务异常,已熔断]} 
    订单信息{id=1,port=6061,stock=库存信息[服务异常,已熔断],score=积分信息[id=1,port=降级] } 
    ```

注意是`/actuator/refresh`，不是1.x版本的`/refresh`
https://www.cnblogs.com/louis2015/p/9821227.html

### 自动刷新
自动刷新需要刷新所有服务节点，在实际应用中难以使用。

- 需要配置bus
- 调用


## 其他
- gateway不要加spring-boot-starter-web包，否则报错。

