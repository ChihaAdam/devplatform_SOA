package org.example.gestionprojets.config;

import org.example.gestionprojets.domain.DeveloppeurEntity;
import org.example.gestionprojets.domain.PlateformeEntity;
import org.example.gestionprojets.domain.ProjetEntity;
import org.example.gestionprojets.domain.TacheEntity;
import org.example.gestionprojets.repository.PlateformeRepository;
import org.example.gestionprojets.repository.ProjetRepository;

/** Jeu de donnees minimal pour que l'interface ait quelque chose a afficher au premier lancement. */
public final class DataInitializer {

    private DataInitializer() {
    }

    public static void seed(String organisation) {
        PlateformeRepository plateformes = new PlateformeRepository();
        ProjetRepository projets = new ProjetRepository();

        if (!plateformes.existsById("DEFAULT")) {
            plateformes.save(new PlateformeEntity("DEFAULT", organisation));
        }
        if (projets.count() > 0) {
            return;
        }

        projets.save(refonte());
        projets.save(apiFacturation());
    }

    private static ProjetEntity refonte() {
        ProjetEntity projet = new ProjetEntity("P-1001", "Refonte du portail", "EN_COURS", "fr");
        projet.addDeveloppeur(new DeveloppeurEntity("D-1", "Camille Martin", "camille.martin@example.org", "BACKEND"));
        projet.addDeveloppeur(new DeveloppeurEntity("D-2", "Inès Benali", "ines.benali@example.org", "FRONTEND"));
        projet.addTache(new TacheEntity("T-1001", "Concevoir le modele de donnees", 8, "TERMINE", "HAUTE", "TACHE"));
        projet.addTache(new TacheEntity("T-1002", "Implementer les endpoints SOAP", 13, "EN_COURS", "HAUTE", "FONCTIONNALITE"));
        projet.addTache(new TacheEntity("T-1003", "Brancher l'interface sur la passerelle", 5, "A_FAIRE", "MOYENNE", "FONCTIONNALITE"));
        return projet;
    }

    private static ProjetEntity apiFacturation() {
        ProjetEntity projet = new ProjetEntity("P-1002", "API de facturation", "A_FAIRE", "fr");
        projet.addDeveloppeur(new DeveloppeurEntity("D-3", "Thomas Leroy", "thomas.leroy@example.org", "BACKEND"));
        projet.addTache(new TacheEntity("T-2001", "Definir les contrats d'interface", 3, "A_FAIRE", "HAUTE", "TACHE"));
        projet.addTache(new TacheEntity("T-2002", "Corriger l'arrondi des montants", 2, "A_FAIRE", "BASSE", "BUG"));
        return projet;
    }
}
