package org.example.gateway.soap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.example.gateway.soap.SoapNames;

@XmlRootElement(name = "Tache", namespace = SoapNames.NS)
@XmlType(name = "Tache", namespace = SoapNames.NS)
@XmlAccessorType(XmlAccessType.FIELD)
public class TacheSoap {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "priorite", required = true)
    private String priorite;

    @XmlAttribute(name = "type", required = true)
    private String type;

    @XmlElement(name = "Titre", required = true)
    private String titre;

    @XmlElement(name = "TempsEstime", required = true)
    private Integer tempsEstime;

    @XmlElement(name = "Etat", required = true)
    private String etat;

    public TacheSoap() {
    }

    public TacheSoap(String id, String titre, Integer tempsEstime, String etat, String priorite, String type) {
        this.id = id;
        this.titre = titre;
        this.tempsEstime = tempsEstime;
        this.etat = etat;
        this.priorite = priorite;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Integer getTempsEstime() {
        return tempsEstime;
    }

    public void setTempsEstime(Integer tempsEstime) {
        this.tempsEstime = tempsEstime;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
