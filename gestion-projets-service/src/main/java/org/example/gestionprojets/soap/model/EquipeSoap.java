package org.example.gestionprojets.soap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.example.gestionprojets.soap.SoapNames;

@XmlRootElement(name = "Equipe", namespace = SoapNames.NS)
@XmlType(name = "Equipe", namespace = SoapNames.NS)
@XmlAccessorType(XmlAccessType.FIELD)
public class EquipeSoap {

    @XmlElement(name = "Developpeur")
    private DeveloppeurSoap[] developpeurs = new DeveloppeurSoap[0];

    public EquipeSoap() {
    }

    public EquipeSoap(DeveloppeurSoap[] developpeurs) {
        this.developpeurs = developpeurs == null ? new DeveloppeurSoap[0] : developpeurs;
    }

    public DeveloppeurSoap[] getDeveloppeurs() {
        return developpeurs;
    }

    public void setDeveloppeurs(DeveloppeurSoap[] developpeurs) {
        this.developpeurs = developpeurs == null ? new DeveloppeurSoap[0] : developpeurs;
    }
}
