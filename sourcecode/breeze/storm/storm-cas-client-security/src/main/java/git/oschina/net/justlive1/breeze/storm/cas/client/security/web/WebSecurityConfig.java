package git.oschina.net.justlive1.breeze.storm.cas.client.security.web;

import java.util.Arrays;

import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.cas.web.authentication.ServiceAuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import git.oschina.net.justlive1.breeze.snow.common.web.base.ConfigProperties;
import git.oschina.net.justlive1.breeze.storm.cas.client.security.auth.CustomAuthenticationFailureHandler;
import git.oschina.net.justlive1.breeze.storm.cas.client.security.auth.CustomAuthenticationSuccessHandler;

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

	@Autowired(required = false)
	CustomAuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired(required = false)
	CustomAuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	AuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	AuthenticationProvider authenticationProvider;

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 设置不拦截规则
		web.ignoring().antMatchers(configProps.ignoreMatchers);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();

		// @formatter:off
		
		http.csrf()
				.disable()
				.authorizeRequests()
					.expressionHandler(expressionHandler)
					.and()
				.exceptionHandling()
					.accessDeniedPage(configProps.accessDeniedUrl)
					.and()
				.httpBasic()
					.authenticationEntryPoint(authenticationEntryPoint)
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

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return new ProviderManager(Arrays.asList(authenticationProvider));
	}

	@Bean
	public CasAuthenticationFilter casAuthenticationFilter(ServiceProperties props, ProxyGrantingTicketStorage storage)
			throws Exception {

		CasAuthenticationFilter filter = new CasAuthenticationFilter();
		filter.setServiceProperties(props);
		filter.setAuthenticationManager(authenticationManager());
		filter.setServiceProperties(props);
		filter.setProxyGrantingTicketStorage(storage);
		filter.setFilterProcessesUrl(configProps.securityFilterProcessesUrl);
		filter.setProxyReceptorUrl(configProps.proxyReceptorUrl);
		filter.setAuthenticationDetailsSource(new ServiceAuthenticationDetailsSource(props));

		AuthenticationFailureHandler failureHandler = authenticationFailureHandler;
		if (failureHandler == null) {
			failureHandler = new SimpleUrlAuthenticationFailureHandler(configProps.accessDeniedUrl);
		}

		AuthenticationSuccessHandler successHandler = authenticationSuccessHandler;
		if (successHandler == null) {
			successHandler = new SimpleUrlAuthenticationSuccessHandler(configProps.defaultSuccessUrl);
		}
		filter.setAuthenticationFailureHandler(failureHandler);

		return filter;
	}
}
