package org.example.gateway.soap;

import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Acces au port SOAP des projets. Le port est cree a la premiere requete :
 * la passerelle peut donc demarrer meme si le service metier n'est pas encore pret.
 */
public final class ProjectsSoapClient {

    private final String endpoint;
    private volatile ProjetSoapPort port;

    public ProjectsSoapClient(String endpoint) {
        this.endpoint = endpoint.endsWith("/") ? endpoint.substring(0, endpoint.length() - 1) : endpoint;
    }

    public ProjetSoapPort port() {
        ProjetSoapPort local = port;
        if (local == null) {
            synchronized (this) {
                local = port;
                if (local == null) {
                    local = createPort();
                    port = local;
                }
            }
        }
        return local;
    }

    private ProjetSoapPort createPort() {
        try {
            Service service = Service.create(
                    new URL(endpoint + "?wsdl"),
                    new QName(SoapNames.NS, "ProjetService")
            );
            return service.getPort(new QName(SoapNames.NS, "ProjetPort"), ProjetSoapPort.class);
        } catch (MalformedURLException e) {
            throw new WebServiceException("URL SOAP invalide : " + endpoint, e);
        }
    }
}
