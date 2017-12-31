package git.oschina.net.justlive1.breeze.cloud.registry.client.eureka;

import com.netflix.appinfo.MyDataCenterInstanceConfig;

import git.oschina.net.justlive1.breeze.cloud.registry.client.RegistryClient;

/**
 * 统一命名空间
 * 
 * @author wubo
 *
 */
public class RegistryInstanceConfig extends MyDataCenterInstanceConfig {

	public RegistryInstanceConfig() {
		super(RegistryClient.NAMESPACE);
	}

	@Override
	public String getIpAddress() {
		return configInstance.getStringProperty(RegistryClient.NAMESPACE + "vipAddress", super.getIpAddress()).get();
	}
}
