
# 认证授权相关服务


## 介绍

- storm-cas 单点登录cas服务
- storm-cas-client 实现直接对接cas的客户端
- storm-cas-client-security 实现spring security和cas整合的客户端
- storm-cas-client-shiro 实现shiro和cas整合的客户端

## 特性

* 基于properties配置进行项目定制
* 极简xml
* 自动装载 依赖jar配置properties无需额外配置
* 支持构建 [Spring Boot](https://projects.spring.io/spring-boot) 和 [Spring Cloud](http://projects.spring.io/spring-cloud/).

## 开发
	// Features 
		storm
			服务注册
			配置中心
			监控预警
			分布式
			负载均衡
		storm-cas
			单点登录    		-ok
			https/http  		-ok
			jdbc认证    		-ok
			tickets
			在线用户统计
			登录验证码
			密码管理
			service-manager
			webflow
		storm-cas-client
			cas认证跳转 		-ok
			退出登录接口
			获取当前登录用户
		storm-cas-client-security
			整合cas和security  	-ok
			cas认证跳转 		-ok
			单点退出存在问题
			security权限
		storm-cas-client-shiro
			整合cas和shiro
	
	// Bugs
		* storm-cas 开启ssl https使用内置tomcat时，配置的http端口无效，配置有误还是不支持？
	
	// Warinings
		* Runtime memory is used as the persistence storage for retrieving and managing tickets. Tickets that are issued during runtime will be LOST upon container restarts. This MAY impact SSO functionality.
		* List of authorized users for admin pages security is not defined. Allowing access for all authenticated users
		* Secret key for encryption is not defined for [Ticket-granting Cookie]
		* Secret key for signing is not defined for [Ticket-granting Cookie]
		* Secret key for signing is not defined under [cas.webflow.crypto.signing.key]
		* Secret key for encryption is not defined under [cas.webflow.crypto.encryption.key].

## 部署
[Release](https://gitee.com/justlive1/earth/releases)

storm-cas打包为可执行war
