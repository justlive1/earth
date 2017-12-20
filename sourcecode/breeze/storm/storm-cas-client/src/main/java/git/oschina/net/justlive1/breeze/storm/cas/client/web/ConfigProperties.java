package git.oschina.net.justlive1.breeze.storm.cas.client.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigProperties {

	@Value("${app.host:}")
	private String appHost;

	@Value("${app.name:}")
	private String appName;

	@Value("${cas.server.prefixUrl:}")
	public String casServerPrefixUrl;

	@Value("${cas.server.loginUrl:}")
	public String loginUrl;

	@Value("${server.name:}")
	public String serverName;

	@Value("${security.ignoreMatchers:}")
	public String[] ignoreMatchers;

	@Value("${security.logoutUrl:}")
	public String logoutUrl;

	@Value("${security.logoutSuccessUrl:}")
	public String logoutSuccessUrl;

	@Value("${security.proxyReceptorUrl:}")
	public String proxyReceptorUrl;

	@Value("${security.accessDeniedUrl:/accessDenied}")
	public String accessDeniedUrl;

	@Value("${security.sessionExpireUrl:/expiredWarn}")
	public String sessionExpireUrl;

	@Value("${security.casAuthProviderKey:casAuthProviderKey}")
	public String casAuthProviderKey;

}
