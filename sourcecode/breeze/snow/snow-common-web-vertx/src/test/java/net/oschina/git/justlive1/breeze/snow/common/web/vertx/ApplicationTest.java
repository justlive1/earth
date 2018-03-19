package net.oschina.git.justlive1.breeze.snow.common.web.vertx;

import org.junit.Test;
import io.vertx.core.Vertx;

/**
 * Test
 * 
 * @author wubo
 *
 */
public class ApplicationTest {

  @Test
  public void testRoute() {

    Vertx.vertx().deployVerticle(VerticleDemo.class.getName());

  }

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(VerticleDemo.class.getName());

  }

}
