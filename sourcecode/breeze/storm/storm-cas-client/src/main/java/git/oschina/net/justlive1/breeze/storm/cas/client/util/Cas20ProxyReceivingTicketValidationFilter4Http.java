package git.oschina.net.justlive1.breeze.storm.cas.client.util;

import javax.net.ssl.HostnameVerifier;

import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;

import git.oschina.net.justlive1.breeze.snow.common.base.util.HostnameVerifierAlwaysTrue;

public class Cas20ProxyReceivingTicketValidationFilter4Http extends Cas20ProxyReceivingTicketValidationFilter {

	private static final HostnameVerifier VERIFIER = new HostnameVerifierAlwaysTrue();

	@Override
	protected HostnameVerifier getHostnameVerifier() {
		return VERIFIER;
	}
}
