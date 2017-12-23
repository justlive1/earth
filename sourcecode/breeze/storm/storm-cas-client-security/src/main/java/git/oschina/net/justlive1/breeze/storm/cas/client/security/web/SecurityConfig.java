package git.oschina.net.justlive1.breeze.storm.cas.client.security.web;

import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorageImpl;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.authentication.EhCacheBasedTicketCache;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;

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
		serviceProperties.setService(configProps.checkService);
		serviceProperties.setAuthenticateAllArtifacts(true);
		serviceProperties.setSendRenew(false);
		return serviceProperties;
	}

	@Bean
	@Primary
	public AuthenticationEntryPoint authenticationEntryPoint(ServiceProperties props) {

		CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
		entryPoint.setLoginUrl(configProps.loginUrl);
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
	public CasAuthenticationProvider casAuthenticationProvider(ServiceProperties props, TicketValidator validator) {

		CasAuthenticationProvider provider = new CasAuthenticationProvider();
		provider.setServiceProperties(props);
		provider.setTicketValidator(validator);
		provider.setUserDetailsService(s -> new User("casuser", "notused", true, true, true, true,
				AuthorityUtils.createAuthorityList("ROLE_ADMIN")));
		provider.setKey(configProps.casAuthProviderKey);
		EhCacheBasedTicketCache statelessTicketCache = new EhCacheBasedTicketCache();
		Cache cache = new Cache(EhCacheBasedTicketCache.class.getName(), 50, true, false, 3600, 900);
		statelessTicketCache.setCache(cache);
		provider.setStatelessTicketCache(statelessTicketCache);
		return provider;
	}

}
