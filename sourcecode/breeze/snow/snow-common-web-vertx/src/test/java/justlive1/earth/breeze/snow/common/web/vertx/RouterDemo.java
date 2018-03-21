package justlive1.earth.breeze.snow.common.web.vertx;

import justlive1.earth.breeze.snow.common.web.vertx.annotation.VertxHeaderParam;
import justlive1.earth.breeze.snow.common.web.vertx.annotation.VertxPathParam;
import justlive1.earth.breeze.snow.common.web.vertx.annotation.VertxRequestBody;
import justlive1.earth.breeze.snow.common.web.vertx.annotation.VertxRequestParam;
import justlive1.earth.breeze.snow.common.web.vertx.annotation.VertxRoute;
import justlive1.earth.breeze.snow.common.web.vertx.annotation.VertxRouteMapping;

@VertxRoute("/demo")
public class RouterDemo {

  @VertxRouteMapping(value = "/test/:path")
  public String test1(@VertxRequestParam("request") String request,
      @VertxHeaderParam("header") String header, @VertxPathParam("path") String path) {
    return String.format("{%s},{%s},{%s}", request, header, path);
  }

  @VertxRouteMapping(value = "/test")
  public String test2(@VertxRequestBody Body body) {
    return String.format("%s", body);
  }
}
