package org.example.gestionprojets.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "taches")
public class TacheEntity {

    @Id
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "temps_estime", nullable = false)
    private Integer tempsEstime;

    @Column(name = "etat", nullable = false, length = 64)
    private String etat;

    @Column(name = "priorite", nullable = false, length = 64)
    private String priorite;

    @Column(name = "type", nullable = false, length = 64)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projet_id", nullable = false)
    private ProjetEntity projet;

    public TacheEntity() {
    }

    public TacheEntity(String id, String titre, Integer tempsEstime, String etat, String priorite, String type) {
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

    public String getTitre() {
        return titre;
    }

    public Integer getTempsEstime() {
        return tempsEstime;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getPriorite() {
        return priorite;
    }

    public String getType() {
        return type;
    }

    public ProjetEntity getProjet() {
        return projet;
    }

    public void setProjet(ProjetEntity projet) {
        this.projet = projet;
    }
}
