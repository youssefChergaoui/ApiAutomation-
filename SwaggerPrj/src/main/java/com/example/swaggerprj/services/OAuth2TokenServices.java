package com.example.swaggerprj.services;


import com.example.swaggerprj.utils.Constants;
import com.example.swaggerprj.utils.HttpUtils;
import com.example.swaggerprj.utils.PropertyReader;
import org.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Credentials;
import okhttp3.FormBody;
import java.io.IOException;

import static com.example.swaggerprj.utils.Constants.*;


public class OAuth2TokenServices {


   /* @Value("${token.url}")
    private  String tokenUrl;
    @Value("${scopes}")
    private  String scopes;
    @Value("${client.secret}")
    private  String clientSecret;
    @Value("${client.id}")
    private  String clientId;*/

    private static final String clientId = PropertyReader.getProperty("client.id");
    private static final String clientSecret = PropertyReader.getProperty("client.secret");
    private static final String tokenUrl = PropertyReader.getProperty("token.url");
    private static final String scopes = PropertyReader.getProperty("scopes");

    public static String oauth2PasswordCredentials(String username, String password) throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .add(Constants.GRANT_TYPE, Constants.PASSWORD_CREDENTIALS)
                .add(USER_NAME, username)
                .add(PASSWORD, password)
                .add(SCOPE, scopes)
                .build();

        String credentials = Credentials.basic(clientId, clientSecret);

        //OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(tokenUrl)
                .addHeader(AUTHORIZATION, credentials)
                .post(requestBody)
                .build();


        try (Response response = HttpUtils.getUnsafeOkHttpClientWithProxy().newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ResponseBody responseBody = response.body();
            if (responseBody == null) throw new IOException("Response body is null");

            String responseString = responseBody.string();
            JSONObject jsonResponse = new JSONObject(responseString);
            return jsonResponse.getString("access_token");
        }
    }

    public static String oauth2PasswordCredentials(String grantType, String username, String password) throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .add(GRANT_TYPE, grantType)
                .add(USER_NAME, username)
                .add(PASSWORD, password)
                .add(SCOPE, scopes)
                .build();

        String credentials = Credentials.basic(clientId, clientSecret);

        Request request = new Request.Builder()
                .url(tokenUrl)
                .addHeader(AUTHORIZATION, credentials)
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();

        try (Response response = HttpUtils.getUnsafeOkHttpClientWithProxy().newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ResponseBody responseBody = response.body();
            if (responseBody == null) throw new IOException("Response body is null");

            String responseString = responseBody.string();
            JSONObject jsonResponse = new JSONObject(responseString);
            return jsonResponse.getString("access_token");
        }
    }
}

