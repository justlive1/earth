
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
	//TODO 功能点
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
			security权限
		storm-cas-client-shiro
			整合cas和shiro

## 部署
[Release](https://gitee.com/justlive1/earth/releases)

storm-cas打包为可执行war
