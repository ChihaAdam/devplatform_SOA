package org.example.gestionprojets.soap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.example.gestionprojets.soap.SoapNames;

@XmlRootElement(name = "Projet", namespace = SoapNames.NS)
@XmlType(name = "Projet", namespace = SoapNames.NS)
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjetSoap {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "lang", required = true)
    private String lang;

    @XmlElement(name = "Nom", required = true)
    private String nom;

    @XmlElement(name = "Statut", required = true)
    private String statut;

    @XmlElement(name = "Equipe")
    private EquipeSoap equipe = new EquipeSoap();

    @XmlElement(name = "Taches")
    private TachesSoap taches = new TachesSoap();

    public ProjetSoap() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public EquipeSoap getEquipe() {
        return equipe;
    }

    public void setEquipe(EquipeSoap equipe) {
        this.equipe = equipe == null ? new EquipeSoap() : equipe;
    }

    public TachesSoap getTaches() {
        return taches;
    }

    public void setTaches(TachesSoap taches) {
        this.taches = taches == null ? new TachesSoap() : taches;
    }
}
