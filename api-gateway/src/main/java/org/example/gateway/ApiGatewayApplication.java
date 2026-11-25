package org.example.gateway;

import org.example.gateway.config.GatewayApplication;
import org.example.gateway.service.ProjectService;
import org.example.gateway.service.SoapProjectService;
import org.example.gateway.soap.ProjectsSoapClient;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import java.net.URI;

/** Point d'entree de la passerelle REST : Jersey + Grizzly, sans framework applicatif. */
public final class ApiGatewayApplication {

    private ApiGatewayApplication() {
    }

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(env("GATEWAY_PORT", env("PORT", "9000")));
        String soapEndpoint = env("PROJETS_SOAP_ENDPOINT", "http://localhost:8081/ws/projets");

        ProjectsSoapClient client = new ProjectsSoapClient(soapEndpoint);
        ProjectService service = new SoapProjectService(client);
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
                URI.create("http://0.0.0.0:" + port + "/"),
                new GatewayApplication(service),
                false
        );
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown, "gateway-shutdown"));
        System.out.println("Passerelle REST prete : http://localhost:" + port + "/api/projects");
    }

    private static String env(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
