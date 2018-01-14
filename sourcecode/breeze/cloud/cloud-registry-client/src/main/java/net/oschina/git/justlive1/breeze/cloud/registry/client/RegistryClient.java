package net.oschina.git.justlive1.breeze.cloud.registry.client;

/**
 * 服务注册客户端接口 <br>
 * 提供不同服务注册实现的统一抽象
 * 
 * @author wubo
 *
 */
public interface RegistryClient {

	/**
	 * 命名空间
	 */
	String NAMESPACE = "earth.registry";

	/**
	 * 是否使用ip替代hostname
	 */
	String PREFER_IP_ADDRESS = NAMESPACE + ".preferIpAddress";

	/**
	 * virtual hostname
	 */
	String VIP_ADDRESS = NAMESPACE + ".vipAddress";

	/**
	 * 是否开启
	 */
	String ENABLED = NAMESPACE + ".enabled";

	/**
	 * 注册服务
	 */
	void register();

	/**
	 * 启动服务
	 */
	void start();

	/**
	 * 停止服务
	 */
	void shutdown();
}
