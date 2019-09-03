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

## 其他
- gateway不要加spring-boot-starter-web包，否则报错。