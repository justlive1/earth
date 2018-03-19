package net.oschina.git.justlive1.breeze.snow.common.web.vertx.support;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import com.google.common.collect.ImmutableList;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import net.oschina.git.justlive1.breeze.snow.common.base.exception.Exceptions;
import net.oschina.git.justlive1.breeze.snow.common.web.vertx.annotation.VertxRoute;
import net.oschina.git.justlive1.breeze.snow.common.web.vertx.annotation.VertxRouteMapping;

/**
 * route注册工厂，用于解析Route相关注解并初始化Route
 * 
 * @author wubo
 * @see VertxRoute
 * @see VertxRouteMapping
 *
 */
@Slf4j
public class RouteRegisterFactory {

  static final String PATH_SEPARATOR = "/";

  private Map<String, RouteWrap> routeWraps;

  private Router router;

  private ParamResolverComposite paramResolver;

  public RouteRegisterFactory(Router router) {
    this.router = router;
    this.routeWraps = new HashMap<>(32);
    this.paramResolver = new ParamResolverComposite(ImmutableList.<MethodParamResolver>builder()
        .add(new RequestParamResolver()).add(new HeaderParamResolver()).build());
  }

  public void execute() {

    Reflections rel = new Reflections();
    Set<Class<?>> classes = rel.getTypesAnnotatedWith(VertxRoute.class);

    for (Class<?> clazz : classes) {
      if (clazz.isAnnotationPresent(VertxRoute.class)) {
        parseVertxRoute(clazz);
      }
    }

    register();

  }

  private void parseVertxRoute(Class<?> clazz) {

    VertxRoute route = clazz.getAnnotation(VertxRoute.class);

    Object bean;
    try {
      bean = clazz.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw Exceptions.wrap(e);
    }
    String root = transferUri(route.value());

    Method[] methods = clazz.getMethods();

    for (Method method : methods) {
      if (method.isAnnotationPresent(VertxRouteMapping.class)) {
        parseVertxRouteMapping(root, method, bean);
      }
    }

  }

  private void parseVertxRouteMapping(String root, Method method, Object bean) {

    VertxRouteMapping routeMapping = method.getAnnotation(VertxRouteMapping.class);
    String[] paths = routeMapping.value();

    String baseUrl = "";

    if (root.length() != 0) {
      baseUrl = PATH_SEPARATOR.concat(root);
    }

    for (String path : paths) {
      String url = "";
      String p = transferUri(path);
      if (p.length() != 0) {
        url = baseUrl.concat(PATH_SEPARATOR).concat(p);
      }

      if (routeWraps.containsKey(url)) {
        throw Exceptions.fail((String) null, String.format("url[%s]重复绑定", url));
      }

      Parameter[] parameters = method.getParameters();
      ParamWrap[] paramWraps = parseVertxRouterParamters(parameters);

      RouteWrap wrap = new RouteWrap(url, method, bean, paramWraps, routeMapping);
      routeWraps.put(url, wrap);
    }

  }

  private ParamWrap[] parseVertxRouterParamters(Parameter[] parameters) {

    ParamWrap[] paramWraps = new ParamWrap[parameters.length];
    for (int i = 0; i < parameters.length; i++) {
      if (paramResolver.supported(parameters[i])) {
        paramWraps[i] = paramResolver.resolve(parameters[i]);
      }
    }
    return paramWraps;
  }

  private void register() {

    for (Map.Entry<String, RouteWrap> entry : routeWraps.entrySet()) {
      RouteWrap wrap = entry.getValue();
      VertxRouteMapping routeMapping = wrap.getRouteMapping();
      Route route = router.route().path(entry.getKey());
      for (String consume : routeMapping.consumes()) {
        route.consumes(consume);
      }
      for (String produce : routeMapping.produces()) {
        route.produces(produce);
      }
      for (HttpMethod method : routeMapping.method()) {
        route.method(method);
      }
      Handler<RoutingContext> handler = ctx -> runWithArgs(wrap, ctx);
      if (routeMapping.blocking()) {
        route.blockingHandler(handler);
      } else {
        route.handler(handler);
      }
      if (log.isDebugEnabled()) {
        log.debug("register url [{}]", entry.getKey());
      }
    }

  }

  private void runWithArgs(RouteWrap wrap, RoutingContext ctx) {

    Object[] args = new Object[wrap.paramWraps.length];

    for (int i = 0; i < wrap.paramWraps.length; i++) {
      ParamWrap param = wrap.paramWraps[i];
      if (param != null) {
        args[i] = paramResolver.render(param, ctx);
      }
    }

    try {
      Object obj = wrap.method.invoke(wrap.bean, args);
      ctx.response().end(obj.toString());
    } catch (Exception e) {
      log.error("", e);
    }
  }

  private static String transferUri(String uri) {
    if (uri.startsWith(PATH_SEPARATOR)) {
      return uri.substring(1);
    }
    return uri;
  }

}
