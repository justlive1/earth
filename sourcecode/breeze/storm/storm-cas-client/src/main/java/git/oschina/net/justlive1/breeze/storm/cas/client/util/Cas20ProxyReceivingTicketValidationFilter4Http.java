package git.oschina.net.justlive1.breeze.storm.cas.client.util;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;

import git.oschina.net.justlive1.breeze.snow.common.web.util.HostnameVerifierAlwaysTrue;

public class Cas20ProxyReceivingTicketValidationFilter4Http extends Cas20ProxyReceivingTicketValidationFilter {

	private static final HostnameVerifier VERIFIER = new HostnameVerifierAlwaysTrue();

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
		return VERIFIER;
	}
}
