package git.oschina.net.justlive1.breeze.cloud.registry.client.eureka;

import com.netflix.discovery.DefaultEurekaClientConfig;

import git.oschina.net.justlive1.breeze.cloud.registry.client.RegistryClient;

/**
 * 统一命名空间
 * 
 * @author wubo
 *
 */
public class RegistryClientConfig extends DefaultEurekaClientConfig {

	public RegistryClientConfig() {
		super(RegistryClient.NAMESPACE);
	}

	// TODO 一些eureka的属性需要mask
}
