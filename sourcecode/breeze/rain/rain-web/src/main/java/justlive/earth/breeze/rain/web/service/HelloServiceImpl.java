package justlive.earth.breeze.rain.web.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import justlive.earth.breeze.rain.api.domian.Hello;
import justlive.earth.breeze.rain.api.service.HelloService;
import justlive.earth.breeze.snow.common.web.base.BaseService;

/**
 * demo
 * 
 * @author wubo
 *
 */
@Service
public class HelloServiceImpl extends BaseService implements HelloService {

  @Value("${rain.service.url}")
  String serviceUrl;

  @Autowired
  @Override
  protected void setTemplate(RestTemplate template) {
    super.setTemplate(template);
  }

  @Override
  public List<String> hello() {

    return this.getForObject(serviceUrl + "/test/hello");
  }

  @Override
  public List<String> world(List<String> world) {

    return this.postJsonForObject(serviceUrl + "/test/world", world);
  }

  @Override
  public List<String> helloworld(Hello hello) {

    return this.formSubmitForObject(serviceUrl + "/test/helloworld", hello);
  }
}
