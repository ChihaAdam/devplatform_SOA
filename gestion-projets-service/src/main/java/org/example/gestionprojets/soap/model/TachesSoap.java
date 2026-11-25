package org.example.gestionprojets.soap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.example.gestionprojets.soap.SoapNames;

@XmlRootElement(name = "Taches", namespace = SoapNames.NS)
@XmlType(name = "Taches", namespace = SoapNames.NS)
@XmlAccessorType(XmlAccessType.FIELD)
public class TachesSoap {

    @XmlElement(name = "Tache")
    private TacheSoap[] taches = new TacheSoap[0];

    public TachesSoap() {
    }

    public TachesSoap(TacheSoap[] taches) {
        this.taches = taches == null ? new TacheSoap[0] : taches;
    }

    public TacheSoap[] getTaches() {
        return taches;
    }

    public void setTaches(TacheSoap[] taches) {
        this.taches = taches == null ? new TacheSoap[0] : taches;
    }
}
