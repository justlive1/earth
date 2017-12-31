package git.oschina.net.justlive1.breeze.cloud.registry.client;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;

/**
 * 服务注册客户端抽象类
 * 
 * @author wubo
 *
 */
public abstract class BaseRegistryClient implements RegistryClient {

	private static final int NON_REGISTED = 0;
	private static final int REGISTED = -1;
	private static final int STARTED = -1;
	private static final int STOPED = -2;

	/**
	 * 使用了@EnableDiscoveryClient会自动配置
	 */
	@Autowired(required = false)
	protected DiscoveryClient client;

	protected volatile int autoRegister = NON_REGISTED;

	/**
	 * 注册服务处理
	 */
	protected abstract void doRegister();

	@PostConstruct
	@Override
	public void register() {
		if (client == null) {
			if (autoRegister == NON_REGISTED) {
				autoRegister = REGISTED;
				doRegister();
			}
		} else {
			if (autoRegister == NON_REGISTED) {
				autoRegister = REGISTED;
			}
		}
	}

	/**
	 * 运行服务
	 */
	protected abstract void doStart();

	@Override
	public void start() {

		if (autoRegister == REGISTED) {
			autoRegister = STARTED;
			doStart();
		}
	}

	/**
	 * 停止服务
	 */
	protected abstract void doShutdown();

	@PreDestroy
	@Override
	public void shutdown() {

		if (autoRegister == STARTED) {
			autoRegister = STOPED;
			doShutdown();
		}
	}
}
