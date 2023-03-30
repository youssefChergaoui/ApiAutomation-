package com.example.swaggerprj.services;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpGenericServices {

    public static String getSwaggerContentFromUrl(String swaggerUrl) throws IOException {
        String swaggerContent = null;
        try {
            URL url = new URL(swaggerUrl);
            InputStream is = url.openStream();
            swaggerContent = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("helooooooooooooo"+swaggerContent+"okkkkkkkkkkkkkkkk");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return swaggerContent;
    }
}
