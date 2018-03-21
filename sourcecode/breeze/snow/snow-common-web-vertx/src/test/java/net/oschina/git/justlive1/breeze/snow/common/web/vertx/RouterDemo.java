package net.oschina.git.justlive1.breeze.snow.common.web.vertx;

import net.oschina.git.justlive1.breeze.snow.common.web.vertx.annotation.VertxHeaderParam;
import net.oschina.git.justlive1.breeze.snow.common.web.vertx.annotation.VertxPathParam;
import net.oschina.git.justlive1.breeze.snow.common.web.vertx.annotation.VertxRequestBody;
import net.oschina.git.justlive1.breeze.snow.common.web.vertx.annotation.VertxRequestParam;
import net.oschina.git.justlive1.breeze.snow.common.web.vertx.annotation.VertxRoute;
import net.oschina.git.justlive1.breeze.snow.common.web.vertx.annotation.VertxRouteMapping;

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
