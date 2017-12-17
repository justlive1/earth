package git.oschina.net.justlive1.breeze.storm.cas.client.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.springframework.web.WebApplicationInitializer;

import git.oschina.net.justlive1.breeze.storm.cas.client.util.PropertiesWrapper;

/**
 * 注册filter和listener
 * 
 * @author wubo
 *
 */
public class CasRegister implements WebApplicationInitializer {

	private static final String SERVER_NAME = "server.name";
	private static final String CAS_SERVER_URL_PREFIX = "cas.server.prefixUrl";
	private static final String CAS_SERVER_LOGINURL = "cas.server.loginUrl";
	private static final String SERVER_NAME_FIELD = "serverName";
	private static final String CAS_SERVER_URL_PREFIX_FIELD = "casServerUrlPrefix";
	private static final String CAS_SERVER_LOGINURL_FIELD = "casServerLoginUrl";
	private static final String ANY_PATH = "/*";

	@Override
	public void onStartup(ServletContext ctx) throws ServletException {

		PropertiesWrapper props = new PropertiesWrapper("classpath*:config/*.properties");

		Map<String, String> params = new HashMap<>();
		params.put(CAS_SERVER_URL_PREFIX_FIELD, props.getProperty(CAS_SERVER_URL_PREFIX));
		params.put(CAS_SERVER_LOGINURL_FIELD, props.getProperty(CAS_SERVER_LOGINURL));
		params.put(SERVER_NAME_FIELD, props.getProperty(SERVER_NAME));

		// 单点退出
		ctx.addListener(SingleSignOutHttpSessionListener.class);

		SingleSignOutFilter signOut = new SingleSignOutFilter();
		signOut.setCasServerUrlPrefix(props.getProperty(CAS_SERVER_URL_PREFIX));
		signOut.setIgnoreInitConfiguration(true);

		ctx.addFilter(SingleSignOutFilter.class.getName(), signOut).addMappingForUrlPatterns(null, true, ANY_PATH);

		// cas认证
		Dynamic auth = ctx.addFilter(AuthenticationFilter.class.getName(), AuthenticationFilter.class);
		auth.setInitParameters(params);
		auth.addMappingForUrlPatterns(null, true, ANY_PATH);

		// ticket
		Dynamic ticket = ctx.addFilter(Cas20ProxyReceivingTicketValidationFilter.class.getName(),
				Cas20ProxyReceivingTicketValidationFilter.class);
		ticket.setInitParameters(params);
		ticket.addMappingForUrlPatterns(null, true, ANY_PATH);

		// 开启 AssertionHolder.getAssertion().getPrincipal().getName();
		ctx.addFilter(AssertionThreadLocalFilter.class.getName(), AssertionThreadLocalFilter.class)
				.addMappingForUrlPatterns(null, true, ANY_PATH);
	}

}
