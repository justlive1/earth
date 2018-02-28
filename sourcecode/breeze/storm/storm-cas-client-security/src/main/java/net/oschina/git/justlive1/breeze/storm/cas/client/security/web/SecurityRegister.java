package net.oschina.git.justlive1.breeze.storm.cas.client.security.web;

import static net.oschina.git.justlive1.breeze.snow.common.web.base.ConfigProperties.ANY_PATH;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.security.config.BeanIds;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.util.WebUtils;

/**
 * security拦截器自动加载
 * 
 * @author wubo
 *
 */
public class SecurityRegister implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext ctx) throws ServletException {

    WebUtils.setWebAppRootSystemProperty(ctx);

    ctx.addFilter(BeanIds.SPRING_SECURITY_FILTER_CHAIN, DelegatingFilterProxy.class)
        .addMappingForUrlPatterns(null, true, ANY_PATH);
  }

}
