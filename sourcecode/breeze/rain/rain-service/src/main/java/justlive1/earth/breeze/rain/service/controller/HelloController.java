package justlive1.earth.breeze.rain.service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import justlive1.earth.breeze.rain.api.domian.Hello;
import justlive1.earth.breeze.rain.api.service.HelloService;
import justlive1.earth.breeze.snow.common.base.domain.Response;

/**
 * demo
 * 
 * @author wubo
 *
 */
@RestController
@RequestMapping("/test")
public class HelloController {

  @Autowired
  HelloService helloService;

  @GetMapping("/hello")
  public Response<List<String>> hello() {

    List<String> res = helloService.hello();

    return Response.success(res);
  }

  @PostMapping("/world")
  public Response<List<String>> world(@RequestBody List<String> list) {

    List<String> res = helloService.world(list);

    return Response.success(res);
  }

  @PostMapping("/helloworld")
  public Response<List<String>> helloworld(Hello hello) {

    List<String> res = helloService.helloworld(hello);

    return Response.success(res);
  }
}
