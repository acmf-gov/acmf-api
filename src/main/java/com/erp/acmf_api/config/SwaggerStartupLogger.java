package com.erp.acmf_api.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SwaggerStartupLogger implements ApplicationListener<ApplicationReadyEvent> {

    private static final String EXTERNAL_IP = "31.97.57.75";

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Environment env = event.getApplicationContext().getEnvironment();

        String port = env.getProperty("server.port", "8080");
        String contextPath = env.getProperty("server.servlet.context-path", "");

        String swaggerPath = "/swagger-ui/index.html";

        String localUrl = "http://localhost:" + port + contextPath + swaggerPath;
        String externalUrl = "http://" + EXTERNAL_IP + ":" + port + contextPath + swaggerPath;

        System.out.println("\n----------------------------------------------------------");
        System.out.println("Swagger UI disponível em:");
        System.out.println("Local:   " + localUrl);
        System.out.println("Externo: " + externalUrl);
        System.out.println("----------------------------------------------------------\n");
    }
}
