package org.example.gateway.soap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.example.gateway.soap.SoapNames;

@XmlRootElement(name = "Developpeur", namespace = SoapNames.NS)
@XmlType(name = "Developpeur", namespace = SoapNames.NS)
@XmlAccessorType(XmlAccessType.FIELD)
public class DeveloppeurSoap {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "role", required = true)
    private String role;

    @XmlElement(name = "Nom", required = true)
    private String nom;

    @XmlElement(name = "Email", required = true)
    private String email;

    public DeveloppeurSoap() {
    }

    public DeveloppeurSoap(String id, String nom, String email, String role) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
