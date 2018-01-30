package net.oschina.git.justlive1.breeze.snow.common.base.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 返回true
 * 
 * @author wubo
 *
 */
public class HostnameVerifierAlwaysTrue extends AbstractTrustAllManager implements HostnameVerifier {

    @Override
    public boolean verify(String hostname, SSLSession session) {
        return false;
    }
}
