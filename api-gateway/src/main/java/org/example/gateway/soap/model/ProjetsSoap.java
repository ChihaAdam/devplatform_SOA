package org.example.gateway.soap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.example.gateway.soap.SoapNames;

@XmlRootElement(name = "Projets", namespace = SoapNames.NS)
@XmlType(name = "Projets", namespace = SoapNames.NS)
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjetsSoap {

    @XmlElement(name = "Projet")
    private ProjetSoap[] projets = new ProjetSoap[0];

    public ProjetsSoap() {
    }

    public ProjetsSoap(ProjetSoap[] projets) {
        this.projets = projets == null ? new ProjetSoap[0] : projets;
    }

    public ProjetSoap[] getProjets() {
        return projets;
    }

    public void setProjets(ProjetSoap[] projets) {
        this.projets = projets == null ? new ProjetSoap[0] : projets;
    }
}
