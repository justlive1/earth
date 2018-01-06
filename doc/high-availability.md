网站的高可用架构

---

# 网站可用性的度量与考核

- 网站的可用性度量
    - 网站不可用时间 = 故障修复时间点 - 故障发现时间点
    - 网站年度可用性指标 = (1 - 网站不可用时间/年度时间) * 100%s
- 网站可用性考核

# 高可用的应用

- 应用层主要处理网站应用的业务逻辑，显著特点是应用的无状态性
- 所谓无状态的应用是指应用服务器不保存业务的上下午信息，仅根据每次请求提交的数据进行相应的业务逻辑处理，多个服务器实例完全对等
- 通过负载均衡进行无状态服务的失效转移
- 应用服务器集群的Session管理
    - Session复制
    - Session绑定
        - 负载均衡原地址Hash算
    - 利用Cookie记录Session
    - Session服务器

# 高可用的服务

- 分级管理
- 超时设置
- 异步调用
- 服务降级
- 幂等性设计

# 高可用的数据

- CAP