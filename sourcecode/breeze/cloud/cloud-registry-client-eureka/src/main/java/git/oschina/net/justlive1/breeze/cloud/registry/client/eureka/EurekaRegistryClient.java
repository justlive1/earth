package git.oschina.net.justlive1.breeze.cloud.registry.client.eureka;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;

import git.oschina.net.justlive1.breeze.cloud.registry.client.BaseRegistryClient;
import lombok.extern.slf4j.Slf4j;

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

	@PostConstruct
	void init() throws IOException {

		// 加载配置信息
		ConfigurationManager.loadPropertiesFromResources(configPath);
		this.register();
	}

	@Override
	protected void doRegister() {

		RegistryInstanceConfig instanceConfig = new RegistryInstanceConfig();
		InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
		applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
		eurekaClient = new DiscoveryClient(applicationInfoManager, new RegistryClientConfig());

		log.info("Registering service to eureka with STARTING status");
		applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.STARTING);

		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// Nothing
		}

		log.info("Done sleeping, now changing status to UP");
		applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);

		log.info("Service Registed ..");
	}

	@Override
	protected void doStart() {

	}

	@Override
	protected void doShutdown() {
		if (eurekaClient != null) {
			log.info("Shutting down client.");
			eurekaClient.shutdown();
		}
	}

}
