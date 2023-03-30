package com.example.swaggerprj.utils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.FileReader;

public class FileUtils {
    private static String convertFileToString(File file) throws IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        return new String(bytes, StandardCharsets.UTF_8);
    }
    // 		"basePath": "/v2"==>context
    private static String findValueInJson(String name,String json){
        //String responseString = responseBody.string();
        //JSONObject jsonResponse = new JSONObject(json);
        //return jsonResponse.getString(name);
        String value=null;


                try {
                    // Ouverture du fichier Swagger
                    FileReader reader = new FileReader("C:\\Users\\L\\Downloads\\swag.yaml");

                    // Utilisation de la bibliothèque Gson pour parser le fichier JSON
                    Gson gson = new Gson();
                    JsonObject swagger = gson.fromJson(reader, JsonObject.class);

                    // Récupération des valeurs
                    value = swagger.get("info").getAsJsonObject().get(name).getAsString();


                    // Fermeture du fichier
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        return value;
    }


}
