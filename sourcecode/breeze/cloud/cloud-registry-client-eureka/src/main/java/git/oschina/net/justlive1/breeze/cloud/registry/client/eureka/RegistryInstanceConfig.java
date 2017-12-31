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

	public String getVipAddress() {

		return configInstance.getStringProperty(RegistryClient.VIP_ADDRESS, super.getIpAddress()).get();
	}

	@Override
	public String getHostName(boolean refresh) {

		return configInstance.getBooleanProperty(RegistryClient.PREFER_IP_ADDRESS, Boolean.FALSE).get() ? getIpAddress()
				: super.getHostName(refresh);
	}
}
