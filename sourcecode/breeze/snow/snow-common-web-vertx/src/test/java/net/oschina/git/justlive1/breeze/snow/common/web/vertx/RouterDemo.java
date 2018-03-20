package net.oschina.git.justlive1.breeze.snow.common.web.vertx;

import net.oschina.git.justlive1.breeze.snow.common.web.vertx.annotation.VertxPathParam;
import net.oschina.git.justlive1.breeze.snow.common.web.vertx.annotation.VertxRequestParam;
import net.oschina.git.justlive1.breeze.snow.common.web.vertx.annotation.VertxRoute;
import net.oschina.git.justlive1.breeze.snow.common.web.vertx.annotation.VertxRouteMapping;

@VertxRoute("/demo")
public class RouterDemo {

  @VertxRouteMapping(value = "/test/:type")
  public String test(@VertxRequestParam("name") String name, @VertxPathParam("type") String type) {
    return name + type;
  }
}
