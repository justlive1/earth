package net.oschina.git.justlive1.breeze.cloud.registry.client.eureka;

import com.netflix.discovery.DefaultEurekaClientConfig;

/**
 * 统一命名空间
 * 
 * @author wubo
 *
 */
public class RegistryClientConfig extends DefaultEurekaClientConfig {

    public RegistryClientConfig() {
        super(EurekaRegistryClient.NAMESPACE);
    }

}
