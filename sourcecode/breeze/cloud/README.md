
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
			
		cloud-admin-server(13000)
            springboot服务管理
            
         cloud-admin-server-eureka(13000)
            springboot服务管理对接eureka服务注册
            
         cloud-admin-client
            springboot服务管理客户端
		
	// Questions
        * cloud-admin-server-eureka 当eureka服务注册进入保护模式，当多个admin-server节点宕机后，启动新节点，该节点还是会请求所有宕机的admin-server
        * macOs下springboot 启动内置端口为10000时，ip绑定失败只能用localhost访问，tcp连接CLOSE_WAIT，怀疑是内置tomcat的bug，握手正常建立的过程中被关闭造成
    

## 部署
[Release](https://gitee.com/justlive1/earth/releases)

