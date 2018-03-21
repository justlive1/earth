package justlive.earth.breeze.rain.api.service;

import java.util.List;
import justlive.earth.breeze.rain.api.domian.Hello;

/**
 * demo
 * 
 * @author wubo
 *
 */
public interface HelloService {

  /**
   * 无参数接口demo
   * 
   * @return
   */
  List<String> hello();

  /**
   * list入参
   * 
   * @param world
   * @return
   */
  List<String> world(List<String> world);

  /**
   * 对象入参
   * 
   * @param hello
   * @return
   */
  List<String> helloworld(Hello hello);
}
