package com.example.swaggerprj;


import com.example.swaggerprj.services.HttpGenericServices;
import com.example.swaggerprj.services.OAuth2TokenServices;
import com.example.swaggerprj.services.PublisherServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SwaggerPrjApplication {
    private static final String CLIENT_ID = "zeTZ10Z0P8N9VbW36KT8LMZP9bsa";
    private static final String CLIENT_SECRET = "xdv85fS_yTVmP_7194vMSvcfxz8a";


    public static void main(String[] args) throws IOException {
            //SpringApplication.run(SwaggerPrjApplication.class, args);
       /* File swaggerFile = new File("C:\\Users\\L\\Downloads\\swagger.yaml");
        String swaggerFileContent = "";
        try {
            swaggerFileContent = convertFileToString(swaggerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SwaggerImporter.importSwagger(swaggerFileContent);*/
       // SwaggerImporter.importSwaggerFromUrl("https://petstore.swagger.io/v2/swagger.json");
        List<String> swaggerUrls = Arrays.asList("https://petstore.swagger.io/v2/swagger.json","https://petstore.swagger.io/v2/swagger.json");
        try {
            String swaggerContent = HttpGenericServices.getSwaggerContentFromUrl("https://petstore.swagger.io/v2/swagger.json");
            //HttpGenericServices.getSwaggerContentFromUrl("https://petstore.swagger.io/v2/swagger.json");
            String accessToken = OAuth2TokenServices.oauth2PasswordCredentials("admin","admin");
            String backendEndpoint = "htpps://test.backend.endpoint";
            PublisherServices.importSwaggerContent(swaggerContent,backendEndpoint,accessToken);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'importation des définitions Swagger : " + e.getMessage());
        }


    }

}
