package org.example.gestionprojets;

import org.example.gestionprojets.config.DataInitializer;
import org.example.gestionprojets.config.SoapEndpointConfig;
import org.example.gestionprojets.persistence.Persistence;
import org.example.gestionprojets.repository.PlateformeRepository;
import org.example.gestionprojets.repository.ProjetRepository;
import org.example.gestionprojets.service.ProjetService;
import org.example.gestionprojets.soap.ProjetSoapEndpoint;

/** Point d'entree du service metier : Hibernate + JAX-WS, sans framework applicatif. */
public final class
GestionProjetsApplication {

    private GestionProjetsApplication() {
    }

    public static void main(String[] args) {
        String organisation = env("ORGANISATION", "DevPlatform");
        int port = Integer.parseInt(env("SOAP_PORT", "8081"));
        String path = env("SOAP_PATH", "/ws/projets");

        Persistence.start();
        DataInitializer.seed(organisation);

        ProjetRepository projets = new ProjetRepository();
        PlateformeRepository plateformes = new PlateformeRepository();
        ProjetService service = new ProjetService(projets);
        ProjetSoapEndpoint endpoint = new ProjetSoapEndpoint(service, plateformes, organisation);

        SoapEndpointConfig.publish(port, path, endpoint);
        System.out.println("WSDL disponible : http://localhost:" + port + path + "?wsdl");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            SoapEndpointConfig.shutdown();
            Persistence.stop();
        }, "soap-shutdown"));
    }

    private static String env(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
