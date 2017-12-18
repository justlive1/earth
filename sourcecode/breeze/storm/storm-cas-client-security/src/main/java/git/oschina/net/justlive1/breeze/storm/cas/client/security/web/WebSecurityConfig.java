package git.oschina.net.justlive1.breeze.storm.cas.client.security.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * security配置
 * 
 * @author wubo
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${security.antMatchers}")
	String antMatchers;

	@Value("${security.loginUrl}")
	String loginUrl;

	@Value("${security.successUrl}")
	String successUrl;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers(antMatchers)
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage(loginUrl)
			.defaultSuccessUrl(successUrl)
			.and()
			.logout()
			.permitAll();

		http.csrf().disable();
	}

}
