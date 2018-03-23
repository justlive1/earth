# earth

整合框架，提供一个应用生态系统，包括服务的注册、发现和治理，应用的监控和预警，持续集成，集群管理等

**项目组成**

- cloud (earth\sourcecode\breeze\cloud)
	- 使用spring cloud整合的云服务
	
- common (earth\sourcecode\breeze\snow)
	- snow-common-base 基础包，提供一些常用工具、统一异常处理等
	- snow-common-web web基础包，挺供基于Spring的web相关Bean
	- snow-common-web-vertx 封装基于vert.x的web开发的Bean
	
- storm (earth\sourcecode\breeze\storm)
	- 提供cas的服务和管理
	- 提供了一系列对接了cas的权限管理client实现

- mist (earth\sourcecode\breeze\mist)
	- 一个自实现的sso服务

- rain (earth\sourcecode\breeze\rain)
	- 只需关注业务开发的脚手架，添加上述相关client即可加入应用生态系统