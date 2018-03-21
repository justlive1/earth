package justlive1.earth.breeze.cloud.registry.client.eureka;

import com.netflix.appinfo.MyDataCenterInstanceConfig;

/**
 * 统一命名空间
 * 
 * @author wubo
 *
 */
public class RegistryInstanceConfig extends MyDataCenterInstanceConfig {

  public RegistryInstanceConfig() {
    super(EurekaRegistryClient.NAMESPACE);
  }

  public String getVipAddress() {

    return configInstance.getStringProperty(EurekaRegistryClient.VIP_ADDRESS, super.getIpAddress())
        .get();
  }

  public boolean isEnabled() {

    return configInstance.getBooleanProperty(EurekaRegistryClient.ENABLED, true).get();
  }

  @Override
  public String getHostName(boolean refresh) {

    return configInstance.getBooleanProperty(EurekaRegistryClient.PREFER_IP_ADDRESS, Boolean.FALSE)
        .get() ? getIpAddress() : super.getHostName(refresh);
  }
}
