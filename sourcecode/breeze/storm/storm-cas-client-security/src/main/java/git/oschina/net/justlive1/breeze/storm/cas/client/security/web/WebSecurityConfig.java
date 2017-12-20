package git.oschina.net.justlive1.breeze.storm.cas.client.security.web;

import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorageImpl;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.authentication.EhCacheBasedTicketCache;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.cas.web.authentication.ServiceAuthenticationDetailsSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import git.oschina.net.justlive1.breeze.storm.cas.client.web.ConfigProperties;
import net.sf.ehcache.Cache;

/**
 * security配置
 * 
 * @author wubo
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	ConfigProperties configProps;

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 设置不拦截规则
		web.ignoring().antMatchers(configProps.ignoreMatchers);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		ServiceProperties props = new ServiceProperties();
		props.setService(configProps.serverName);
		props.setAuthenticateAllArtifacts(true);
		props.setSendRenew(true);

		// cas切入点
		CasAuthenticationEntryPoint casPoint = new CasAuthenticationEntryPoint();
		casPoint.setLoginUrl(configProps.loginUrl);
		casPoint.setServiceProperties(props);

		ProxyGrantingTicketStorage storage = new ProxyGrantingTicketStorageImpl();

		// cas认证过滤器
		CasAuthenticationFilter filter = new CasAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager());
		filter.setServiceProperties(props);
		filter.setProxyGrantingTicketStorage(storage);
		filter.setProxyReceptorUrl(configProps.proxyReceptorUrl);
		filter.setAuthenticationDetailsSource(new ServiceAuthenticationDetailsSource(props));
		filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(configProps.accessDeniedUrl));

		// ticket验证
		Cas20ProxyTicketValidator validator = new Cas20ProxyTicketValidator(configProps.casServerPrefixUrl);
		validator.setAcceptAnyProxy(true);
		validator.setProxyCallbackUrl(configProps.proxyReceptorUrl);
		validator.setProxyGrantingTicketStorage(storage);

		// cas认证提供器
		CasAuthenticationProvider casAuthProvider = new CasAuthenticationProvider();
		casAuthProvider.setServiceProperties(props);
		casAuthProvider.setKey(configProps.casAuthProviderKey);
		casAuthProvider
				.setAuthenticationUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService()));
		casAuthProvider.setTicketValidator(validator);

		EhCacheBasedTicketCache statelessTicketCache = new EhCacheBasedTicketCache();
		Cache cache = new Cache(EhCacheBasedTicketCache.class.getName(), 50, true, false, 3600, 900);
		statelessTicketCache.setCache(cache);
		casAuthProvider.setStatelessTicketCache(statelessTicketCache);

		DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();

		// @formatter:off
		http.csrf()
				.disable()
			.httpBasic()
				.authenticationEntryPoint(casPoint)
				.and()
			.addFilter(filter)
			.authenticationProvider(casAuthProvider)
			.authorizeRequests()
				.expressionHandler(expressionHandler)
				.anyRequest()
					.authenticated()
					.and()
				.exceptionHandling()
					.accessDeniedPage(configProps.accessDeniedUrl)
					.and()
				.logout()
					.logoutUrl(configProps.logoutUrl)
					.logoutSuccessUrl(configProps.logoutSuccessUrl)
					.invalidateHttpSession(true)
					.permitAll()
					.and()
				.sessionManagement()
					.sessionFixation()
					.changeSessionId()  
	                .maximumSessions(1)
	                .expiredUrl(configProps.sessionExpireUrl);

		// @formatter:on
	}

}
