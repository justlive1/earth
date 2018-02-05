package net.oschina.git.justlive1.breeze.snow.common.base.util;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.oschina.git.justlive1.breeze.snow.common.base.exception.Exceptions;

/**
 * 信任所有证书
 * 
 * @author wubo
 *
 */
public abstract class AbstractTrustAllManager {

    static {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
                // Nothing
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
                // Nothing
            }
        } };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            throw Exceptions.wrap(e);
        }
    }
}
