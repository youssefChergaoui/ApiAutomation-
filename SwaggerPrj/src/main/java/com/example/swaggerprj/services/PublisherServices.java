package com.example.swaggerprj.services;


import com.example.swaggerprj.models.AdditionalProperties;
import com.example.swaggerprj.models.EndpointConfig;
import com.example.swaggerprj.models.Endpoints;
import com.example.swaggerprj.models.Revision;
import com.example.swaggerprj.utils.Constants;
import com.example.swaggerprj.utils.HttpUtils;
import com.example.swaggerprj.utils.PropertyReader;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

import static com.example.swaggerprj.utils.Constants.*;

public class PublisherServices {
    private static final String IMPORT_API_ENDPOINT = PropertyReader.getProperty("import.api.endpoint");
    public static String importSwaggerContent(String swaggerFileContent, String backendEndpoint, String accesToken) throws IOException {
        AdditionalProperties additionalProperties = new AdditionalProperties(
                "ApiName",
                "api",
                DEFAULT_API_VERSION
                );
        //TODO: Ask joel about: titre version context backendPoint
        Endpoints production_endpoints = new Endpoints(backendEndpoint);
        Endpoints sandbox_endpoints = new Endpoints(backendEndpoint);
        EndpointConfig endpointConfig = new EndpointConfig(
                HTTP_SCHEME,
                production_endpoints,
                sandbox_endpoints);
        additionalProperties.setEndpointConfig(endpointConfig);
        String json = new Gson().toJson(additionalProperties);
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(INLINEAPIDEFINITION, swaggerFileContent)
                .addFormDataPart(ADDITIONALPROPERTIES, json)
                .build();
        Request request = new Request.Builder()
                .url(IMPORT_API_ENDPOINT)
                .post(body)
                .addHeader(AUTHORIZATION, BEARER + SPACE_SEPARATOR + accesToken)
                .build();
        Response response = null;
        try {
            response = HttpUtils.getUnsafeOkHttpClientWithProxy().newCall(request).execute();
        } catch (Exception e) {
            System.out.println("Échec de l'importation de la définition Swagger. Réponse : " + response.code() + " - " + response.body().string());
            return null;
        }
        System.out.println("Importation réussie :" + response.code() + " - " + response.body().string());
        return "";
    }

    public static List<Revision> getRevisionsOfApi(String apiId, String accessToken){
        return null;
    }

    public static void deleteRevision(String apiId, String revisionId, String accessToken){

    }

    public static String createRevision(String apiId, String accessToken){
        String revisionId="";
        return revisionId;
    }
}
