package org.example.gestionprojets.soap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.example.gestionprojets.soap.SoapNames;

/** Racine du document de donnees, exactement comme dans devplatform.xsd. */
@XmlRootElement(name = "DevPlatform", namespace = SoapNames.NS)
@XmlType(name = "DevPlatform", namespace = SoapNames.NS)
@XmlAccessorType(XmlAccessType.FIELD)
public class DevPlatformSoap {

    @XmlAttribute(name = "organisation", required = true)
    private String organisation;

    @XmlElement(name = "Projets")
    private ProjetsSoap projets = new ProjetsSoap();

    public DevPlatformSoap() {
    }

    public DevPlatformSoap(String organisation, ProjetsSoap projets) {
        this.organisation = organisation;
        this.projets = projets == null ? new ProjetsSoap() : projets;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public ProjetsSoap getProjets() {
        return projets;
    }

    public void setProjets(ProjetsSoap projets) {
        this.projets = projets == null ? new ProjetsSoap() : projets;
    }
}
