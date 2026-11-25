package org.example.gestionprojets.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "projets")
public class ProjetEntity {

    @Id
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "statut", nullable = false, length = 64)
    private String statut;

    @Column(name = "lang", nullable = false, length = 32)
    private String lang;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("id ASC")
    private Set<DeveloppeurEntity> developpeurs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("id ASC")
    private Set<TacheEntity> taches = new LinkedHashSet<>();

    public ProjetEntity() {
    }

    public ProjetEntity(String id, String nom, String statut, String lang) {
        this.id = id;
        this.nom = nom;
        this.statut = statut;
        this.lang = lang;
    }

    public String getId() {
        return id;
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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Set<DeveloppeurEntity> getDeveloppeurs() {
        return developpeurs;
    }

    public Set<TacheEntity> getTaches() {
        return taches;
    }

    public void addDeveloppeur(DeveloppeurEntity developpeur) {
        developpeur.setProjet(this);
        developpeurs.add(developpeur);
    }

    public void addTache(TacheEntity tache) {
        tache.setProjet(this);
        taches.add(tache);
    }

    public Optional<TacheEntity> findTache(String tacheId) {
        return taches.stream().filter(tache -> tache.getId().equals(tacheId)).findFirst();
    }
}
