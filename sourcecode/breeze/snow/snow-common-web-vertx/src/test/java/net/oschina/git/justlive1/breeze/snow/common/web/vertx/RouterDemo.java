package net.oschina.git.justlive1.breeze.snow.common.web.vertx;

import net.oschina.git.justlive1.breeze.snow.common.web.vertx.annotation.VertxRouteMapping;
import net.oschina.git.justlive1.breeze.snow.common.web.vertx.annotation.VertxRoute;

@VertxRoute("/demo")
public class RouterDemo {

  @VertxRouteMapping(value = "/test")
  public String test() {
    return "test";
  }
}
