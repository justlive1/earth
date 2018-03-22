package justlive.earth.breeze.snow.common.web.vertx;

import justlive.earth.breeze.snow.common.web.vertx.annotation.VertxHeaderParam;
import justlive.earth.breeze.snow.common.web.vertx.annotation.VertxPathParam;
import justlive.earth.breeze.snow.common.web.vertx.annotation.VertxRequestBody;
import justlive.earth.breeze.snow.common.web.vertx.annotation.VertxRequestParam;
import justlive.earth.breeze.snow.common.web.vertx.annotation.VertxRoute;
import justlive.earth.breeze.snow.common.web.vertx.annotation.VertxRouteMapping;

@VertxRoute("/demo")
public class RouterDemo {

  @VertxRouteMapping(value = "/test/:path")
  public String test1(@VertxRequestParam("request") String request,
      @VertxHeaderParam("header") Boolean header, @VertxPathParam("path") Long path) {
    return String.format("{%s},{%s},{%s}", request, header, path);
  }

  @VertxRouteMapping(value = "/test")
  public String test2(@VertxRequestBody Body body) {
    return String.format("%s", body);
  }
}
