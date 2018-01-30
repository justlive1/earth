package net.oschina.git.justlive1.breeze.cloud.registry.client.eureka;

import com.netflix.appinfo.MyDataCenterInstanceConfig;

import net.oschina.git.justlive1.breeze.cloud.registry.client.RegistryClient;

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

    public boolean isEnabled() {

        return configInstance.getBooleanProperty(RegistryClient.ENABLED, true).get();
    }

    @Override
    public String getHostName(boolean refresh) {

        return configInstance.getBooleanProperty(RegistryClient.PREFER_IP_ADDRESS, Boolean.FALSE).get() ? getIpAddress()
                : super.getHostName(refresh);
    }
}
