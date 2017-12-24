package git.oschina.net.justlive1.breeze.snow.common.base.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class HostnameVerifierAlwaysTrue extends TrustAllManager implements HostnameVerifier {

	@Override
	public boolean verify(String hostname, SSLSession session) {
		return false;
	}
}
