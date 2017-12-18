package git.oschina.net.justlive1.breeze.storm.cas.client.web;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterRegistration.Dynamic;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
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
		Dynamic ticket = ctx.addFilter(
				Cas20ProxyReceivingTicketValidationFilter4HostnameVerifierAlawysTrue.class.getName(),
				Cas20ProxyReceivingTicketValidationFilter4HostnameVerifierAlawysTrue.class);
		ticket.setInitParameters(params);
		ticket.addMappingForUrlPatterns(null, true, ANY_PATH);

		// 开启 AssertionHolder.getAssertion().getPrincipal().getName();
		ctx.addFilter(AssertionThreadLocalFilter.class.getName(), AssertionThreadLocalFilter.class)
				.addMappingForUrlPatterns(null, true, ANY_PATH);
	}

	public static class Cas20ProxyReceivingTicketValidationFilter4HostnameVerifierAlawysTrue
			extends Cas20ProxyReceivingTicketValidationFilter {

		static {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };

			// Install the all-trusting trust manager
			try {
				SSLContext sc = SSLContext.getInstance("TLS");
				sc.init(null, trustAllCerts, new SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		protected HostnameVerifier getHostnameVerifier() {
			return new HostnameVerifier() {
				@Override
				public boolean verify(String var1, SSLSession var2) {
					return true;
				}
			};
		}
	}
}
