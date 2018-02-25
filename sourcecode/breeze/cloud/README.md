
# 云服务


## 介绍

- cloud-admin-* 服务管理
- cloud-config 配置中心
- cloud-registry-* 服务注册
- cloud-security-support 包含security的ui和配置



## 特性

* 基于properties配置进行项目定制
* 极简xml
* 自动装载 依赖jar配置properties无需额外配置
* 支持构建 [Spring Boot](https://projects.spring.io/spring-boot) 和 [Spring Cloud](http://projects.spring.io/spring-cloud/).

## 开发
	
```
	//TODO 功能点

        cloud
            服务注册
            配置中心
            监控预警
            分布式
            负载均衡
            docker
            webHooks
		
        cloud-config(11000)
            native配置  
            远程仓库配置 
            jdbc配置
            health
            security   
            加密解密       
            plain text
            嵌入式
            cloud bus
            client
		
        cloud-registry-server-eureka(12000)
            基于eureka的发现服务
            client
		
        cloud-registry-client
            注册服务client的抽象

        cloud-registry-client-consul
            基于consul的服务注册client
		
        cloud-registry-client-eureka
            基于eureka的服务注册client
            注册服务
            rest loadbalance
	    
        cloud-registry-client-zookeeper
            基于zookeeper的服务注册client

        cloud-admin-server(13000)
            springboot服务管理

        cloud-admin-server-consul(13000)
            springboot服务管理对接consul服务注册
                        
        cloud-admin-server-eureka(13000)
            springboot服务管理对接eureka服务注册
        
        cloud-admin-server-zookeeper(13000)
            springboot服务管理对接zookeeper服务注册
            
        cloud-admin-client
            springboot服务管理客户端
		
        cloud-security-support
            包含login-ui、security、actuator的依赖
            SecurityConf配置formlogin，需要spring.boot.auth.enabled=true开启
            springcloud项目依赖需要@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })

```

## 部署
[Release](https://gitee.com/justlive1/earth/releases)

