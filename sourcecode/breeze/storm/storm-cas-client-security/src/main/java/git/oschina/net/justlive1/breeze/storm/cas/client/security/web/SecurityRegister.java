package git.oschina.net.justlive1.breeze.storm.cas.client.security.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.util.WebUtils;

import static git.oschina.net.justlive1.breeze.storm.cas.client.web.ConfigProperties.ANY_PATH;
import static git.oschina.net.justlive1.breeze.storm.cas.client.web.ConfigProperties.SPRING_SECURITY_FILTER_CHAIN;

public class SecurityRegister implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext ctx) throws ServletException {

		WebUtils.setWebAppRootSystemProperty(ctx);

		ctx.addFilter(SPRING_SECURITY_FILTER_CHAIN, DelegatingFilterProxy.class).addMappingForUrlPatterns(null, true,
				ANY_PATH);

	}

}
