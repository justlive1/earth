package git.oschina.net.justlive1.breeze.snow.common.web.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class HostnameVerifierAlwaysTrue implements HostnameVerifier {

	@Override
	public boolean verify(String hostname, SSLSession session) {
		return false;
	}
}
