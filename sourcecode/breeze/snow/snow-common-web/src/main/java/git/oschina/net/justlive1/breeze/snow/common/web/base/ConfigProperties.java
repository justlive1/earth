package git.oschina.net.justlive1.breeze.snow.common.web.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigProperties {

	public static final String DEFAULT_CONFIG_PATH = "classpath*:config/*.properties";

	public static final String SERVER_NAME = "server.name";
	public static final String SERVER_NAME_FIELD = "serverName";

	public static final String CAS_SERVER_URL_PREFIX = "cas.server.prefixUrl";
	public static final String CAS_SERVER_LOGINURL = "cas.server.loginUrl";
	public static final String CAS_SERVER_URL_PREFIX_FIELD = "casServerUrlPrefix";
	public static final String CAS_SERVER_LOGINURL_FIELD = "casServerLoginUrl";

	public static final String IGNORES_URLS = "app.ignoreMatchers";
	public static final String IGNORES_URLS_FIELD = "ignorePattern";

	public static final String ANY_PATH = "/*";

	@Value("${app.host:}")
	public String appHost;

	@Value("${app.name:}")
	public String appName;

	@Value("${app.ignoreMatchers:}")
	public String[] ignoreMatchers;

	@Value("${cas.server.prefixUrl:}")
	public String casServerPrefixUrl;

	@Value("${cas.server.loginUrl:}")
	public String loginUrl;

	@Value("${security.checkService:}")
	public String checkService;

	@Value("${security.filterProcessesUrl:/j_spring_cas_security_check}")
	public String securityFilterProcessesUrl;

	@Value("${security.username:username}")
	public String securityUserName;

	@Value("${security.password:password}")
	public String securityPassword;

	@Value("${security.loginPage:/login}")
	public String securityLoginPage;

	@Value("${security.defaultSuccessUrl:/}")
	public String defaultSuccessUrl;

	@Value("${security.authenticationFailureUrl:/login?error}")
	public String failureUrl;

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
