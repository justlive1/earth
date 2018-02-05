package net.oschina.git.justlive1.breeze.cloud.registry.client.eureka;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.stereotype.Component;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;

import lombok.extern.slf4j.Slf4j;
import net.oschina.git.justlive1.breeze.cloud.registry.client.BaseRegistryClient;
import net.oschina.git.justlive1.breeze.snow.common.base.exception.Exceptions;

/**
 * Eureka服务注册client实现
 * 
 * @author wubo
 *
 */
@Slf4j
@Singleton
@Component
public class EurekaRegistryClient extends BaseRegistryClient {

    private ApplicationInfoManager applicationInfoManager;
    private EurekaClient eurekaClient;

    @Value("${earth.registry.configPath:config/application.properties}")
    private String configPath;

    @Override
    protected void doRegister() {

        // 加载配置信息
        try {
            ConfigurationManager.loadPropertiesFromResources(configPath);
        } catch (IOException e) {
            throw Exceptions.wrap(e);
        }

        RegistryInstanceConfig instanceConfig = new RegistryInstanceConfig();

        if (!instanceConfig.isEnabled()) {
            return;
        }

        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        eurekaClient = new DiscoveryClient(applicationInfoManager, new RegistryClientConfig());

        log.info("Registering service to eureka with STARTING status");
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.STARTING);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Done sleeping, now changing status to UP");
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);

        this.client = new EurekaDiscoveryClient(instanceConfig, eurekaClient);

        InstanceInfo nextServerInfo = null;
        while (nextServerInfo == null) {
            try {
                nextServerInfo = eurekaClient.getNextServerFromEureka(instanceConfig.getVipAddress(), false);
            } catch (Exception e) {

                log.info("Waiting ... verifying service registration with eureka ...", e);
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        log.info("Service Registed ..");
    }

    @Override
    protected void doStart() {
        // Nothing
    }

    @Override
    protected void doShutdown() {
        if (eurekaClient != null) {
            log.info("Shutting down client.");
            eurekaClient.shutdown();
        }
    }

}
