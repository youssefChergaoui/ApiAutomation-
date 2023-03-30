package com.example.swaggerprj.utils;


import okhttp3.*;
import javax.net.ssl.*;

public class HttpUtils {
   /* private static Authenticator proxyAuthenticator = new Authenticator() {
        @Override
        public Request authenticate(Route route, Response response) throws IOException {
            String credential = Credentials.basic(Constants.PROXY_USERNAME, Constants.PROXY_PASSWORD);
            return response.request().newBuilder()
                    .header(Constants.PROXY_AUTHORIZATION_HEADER, credential)
                    .build();
        }
    };*/

    public static OkHttpClient getUnsafeOkHttpClientWithProxy() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance(Constants.SSL_CONTEXT);
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            /*builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(Constants.PROXY_HOST, Constants.PROXY_PORT)))
                    .proxyAuthenticator(proxyAuthenticator)
                    .build();*/
            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}