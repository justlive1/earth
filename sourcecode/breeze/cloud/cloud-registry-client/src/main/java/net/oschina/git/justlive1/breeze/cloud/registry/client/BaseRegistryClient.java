package net.oschina.git.justlive1.breeze.cloud.registry.client;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClient;

/**
 * 服务注册客户端抽象类
 * 
 * @author wubo
 *
 */
public abstract class BaseRegistryClient implements RegistryClient {

  private static final int NON_REGISTED = 0;
  private static final int REGISTED = -1;
  private static final int STARTED = -2;
  private static final int STOPED = -3;

  /**
   * 使用了@EnableDiscoveryClient会自动配置
   */
  @Autowired(required = false)
  protected DiscoveryClient client;

  @Autowired(required = false)
  protected RegistryHandler handler;

  protected volatile int autoRegister = NON_REGISTED;

  /**
   * 非springcloud项目client为null<br>
   * springcloud项目自动注册服务中心client不为null<br>
   * 默认排除SimpleDiscoveryClient客户端<br>
   * 特殊处理@Override此方法
   * 
   * @return
   */
  protected boolean needRegister() {

    if (handler != null) {
      return handler.needRegister();
    }

    if (client == null || SimpleDiscoveryClient.class.isInstance(client)) {
      return true;
    }

    if (CompositeDiscoveryClient.class.isInstance(client)) {

      List<DiscoveryClient> clients = ((CompositeDiscoveryClient) client).getDiscoveryClients();
      if (clients == null || clients.isEmpty()) {
        return true;
      }

      int count = 0;
      for (DiscoveryClient c : clients) {
        if (!SimpleDiscoveryClient.class.isInstance(c)) {
          count++;
        }
      }
      return count == 0;
    }

    return false;
  }

  /**
   * 注册服务处理
   */
  protected abstract void doRegister();

  @PostConstruct
  @Override
  public void register() {
    if (needRegister()) {
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
