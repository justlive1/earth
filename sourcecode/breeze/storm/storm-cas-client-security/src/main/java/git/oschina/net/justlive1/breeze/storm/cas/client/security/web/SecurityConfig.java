package git.oschina.net.justlive1.breeze.storm.cas.client.security.web;

import java.sql.Driver;

import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorageImpl;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.authentication.EhCacheBasedTicketCache;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;

import git.oschina.net.justlive1.breeze.snow.common.base.util.DataSourceUtils;
import git.oschina.net.justlive1.breeze.snow.common.web.base.ConfigProperties;
import net.sf.ehcache.Cache;

/**
 * security配置
 * 
 * @author wubo
 *
 */
@Configuration
public class SecurityConfig {

	@Autowired
	ConfigProperties configProps;

	@Bean
	public ServiceProperties serviceProperties() {
		ServiceProperties serviceProperties = new ServiceProperties();
		serviceProperties.setService(configProps.casService);
		serviceProperties.setAuthenticateAllArtifacts(true);
		serviceProperties.setSendRenew(false);
		return serviceProperties;
	}

	@Bean
	@Primary
	public AuthenticationEntryPoint authenticationEntryPoint(ServiceProperties props) {

		CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
		entryPoint.setLoginUrl(configProps.casLoginUrl);
		entryPoint.setServiceProperties(props);
		return entryPoint;
	}

	@Bean
	ProxyGrantingTicketStorage storage() {
		return new ProxyGrantingTicketStorageImpl();
	}

	@Bean
	public TicketValidator ticketValidator(ProxyGrantingTicketStorage storage) {

		Cas20ProxyTicketValidator validator = new Cas20ProxyTicketValidator(configProps.casServerPrefixUrl);
		validator.setAcceptAnyProxy(true);
		validator.setProxyCallbackUrl(configProps.proxyReceptorUrl);
		validator.setProxyGrantingTicketStorage(storage);

		return validator;
	}

	@Bean
	public CasAuthenticationProvider casAuthenticationProvider(ServiceProperties props, TicketValidator validator,
			UserDetailsService userDetailsService) {

		CasAuthenticationProvider provider = new CasAuthenticationProvider();
		provider.setServiceProperties(props);
		provider.setTicketValidator(validator);
		provider.setUserDetailsService(userDetailsService);
		provider.setKey(configProps.casAuthProviderKey);
		EhCacheBasedTicketCache statelessTicketCache = new EhCacheBasedTicketCache();
		Cache cache = new Cache(EhCacheBasedTicketCache.class.getName(), 50, true, false, 3600, 900);
		statelessTicketCache.setCache(cache);
		provider.setStatelessTicketCache(statelessTicketCache);
		return provider;
	}

	@Bean
	@Profile("mock")
	UserDetailsService userDetailsService() {
		return name -> new User(name, name, true, true, true, true, AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
	}

	@Bean
	@Profile("jdbc")
	UserDetailsService JdbcUserDetailsService() {

		Driver driver = DataSourceUtils.fromName(configProps.authDataSourceDriver);
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource(driver, configProps.authDataSourceUrl,
				configProps.authDataSourceUsername, configProps.authDataSourcePassword);

		JdbcDaoImpl jdbcDao = new JdbcDaoImpl();

		jdbcDao.setDataSource(dataSource);
		jdbcDao.setUsersByUsernameQuery(configProps.usersByUsernameQuery);

		if (StringUtils.hasText(configProps.groupAuthoritiesByUsernameQuery)) {
			jdbcDao.setEnableAuthorities(true);
			jdbcDao.setAuthoritiesByUsernameQuery(configProps.authoritiesByUsernameQuery);
		} else {
			jdbcDao.setEnableAuthorities(false);
		}

		if (StringUtils.hasText(configProps.groupAuthoritiesByUsernameQuery)) {
			jdbcDao.setEnableGroups(true);
			jdbcDao.setGroupAuthoritiesByUsernameQuery(configProps.groupAuthoritiesByUsernameQuery);
		}

		return jdbcDao;
	}

}
