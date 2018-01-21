package net.oschina.git.justlive1.breeze.cloud.registry.client;

/**
 * 注册服务自定义处理
 * 
 * @author wubo
 *
 */
public interface RegistryHandler {

	/**
	 * 是否需要注册
	 * 
	 * @return
	 */
	boolean needRegister();
}
