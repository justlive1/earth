package justlive1.earth.breeze.cloud.registry.client;

/**
 * 服务注册客户端接口 <br>
 * 提供不同服务注册实现的统一抽象
 * 
 * @author wubo
 *
 */
public interface RegistryClient {

  /**
   * 注册服务
   */
  void register();

  /**
   * 启动服务
   */
  void start();

  /**
   * 停止服务
   */
  void shutdown();
}
