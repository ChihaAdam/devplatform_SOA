package org.example.gestionprojets.config;

import jakarta.xml.ws.Endpoint;
import org.example.gestionprojets.soap.ProjetSoapEndpoint;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Publie le port SOAP sur le serveur HTTP JDK (8081 par defaut). */
public final class SoapEndpointConfig {

    private static Endpoint endpoint;
    private static ExecutorService executor;

    private SoapEndpointConfig() {
    }

    public static void publish(int port, String path, ProjetSoapEndpoint implementor) {
        // Les threads du serveur HTTP JDK doivent voir le meme classloader que l'application :
        // JAXB trouve ainsi son implementation meme dans le jar executable.
        ClassLoader applicationLoader = Thread.currentThread().getContextClassLoader();
        executor = Executors.newFixedThreadPool(8, runnable -> {
            Thread thread = new Thread(runnable, "soap-worker");
            thread.setContextClassLoader(applicationLoader);
            return thread;
        });

        endpoint = Endpoint.create(implementor);
        endpoint.setExecutor(executor);
        endpoint.publish("http://localhost:" + port + path);
    }

    public static void shutdown() {
        if (endpoint != null) {
            endpoint.stop();
        }
        if (executor != null) {
            executor.shutdownNow();
        }
    }
}
