
# 云服务


## 介绍

- cloud-config 配置中心
- cloud-registry 服务注册中心


## 特性

* 基于properties配置进行项目定制
* 极简xml
* 自动装载 依赖jar配置properties无需额外配置
* 支持构建 [Spring Boot](https://projects.spring.io/spring-boot) 和 [Spring Cloud](http://projects.spring.io/spring-cloud/).

## 开发
	//TODO 功能点
		cloud
			服务注册
			配置中心
			监控预警
			分布式
			负载均衡
			docker
			webHooks
		
		cloud-admin-server(10000)
			springboot服务管理
			
		cloud-admin-client
			springboot服务管理客户端
		
		cloud-config(11000)
			native配置	-ok
			远程仓库配置	-ok
			jdbc配置
			health
			security	-ok
			加密解密		-ok
			plain text
			嵌入式
			cloud bus
			client
		
		cloud-registry(12000)
			发现服务
			client
		
		cloud-registry-client
			注册服务client的抽象
		
		cloud-registry-client-eureka
			基于eureka的服务注册client
			注册服务		-ok
			rest loadbalance
			

## 部署
[Release](https://gitee.com/justlive1/earth/releases)

