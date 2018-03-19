package net.oschina.git.justlive1.breeze.snow.common.web.vertx;

import java.util.Set;
import com.google.common.collect.ImmutableSet;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.CorsHandler;
import net.oschina.git.justlive1.breeze.snow.common.base.constant.BaseConstants;
import net.oschina.git.justlive1.breeze.snow.common.web.vertx.support.RouteRegisterFactory;

/**
 * verticleTest
 * 
 * @author wubo
 *
 */
public class VerticleDemo extends AbstractVerticle {

  @Override
  public void start() throws Exception {

    Router router = Router.router(vertx);

    Set<HttpMethod> methods = ImmutableSet.<HttpMethod>builder().add(HttpMethod.GET)
        .add(HttpMethod.POST).add(HttpMethod.OPTIONS).add(HttpMethod.PUT).add(HttpMethod.DELETE)
        .add(HttpMethod.HEAD).build();

    /** 添加跨域的方法 **/
    router.route().handler(CorsHandler.create(BaseConstants.ANY).allowedMethods(methods));
    router.route().handler(CookieHandler.create());
    router.route().handler(BodyHandler.create());

    RouteRegisterFactory routerAdapter = new RouteRegisterFactory(router);
    routerAdapter.execute();

    vertx.createHttpServer().requestHandler(router::accept).listen(8080);

  }
}
