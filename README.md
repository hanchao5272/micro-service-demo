## 服务清单
| 服务 | 描述 | 起始端口号 | 备注 |
| ---- | ---------- | ---- | ---- |
| eureka-server | 服务注册中心 | 9090 |  |
| gateway-server | 网关 | 8080 |  |
| config-server | 配置管理中心 | 7070 |  |
| service-order | 订单服务 | 6060 |  |
| service-stock | 库存服务 | 5050 |  |
| service-score | 积分服务 | 4040 |  |

## 步骤清单

- done: 服务注册与发现、高可用eureka-server、高可用service-order
- 服务间调用 feign
