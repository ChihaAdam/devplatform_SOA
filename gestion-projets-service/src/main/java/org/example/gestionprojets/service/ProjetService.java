package org.example.gestionprojets.service;

import org.example.gestionprojets.domain.ProjetEntity;
import org.example.gestionprojets.domain.TacheEntity;
import org.example.gestionprojets.repository.ProjetRepository;

import java.util.List;
import java.util.UUID;

public class ProjetService {

    private final ProjetRepository repository;

    public ProjetService(ProjetRepository repository) {
        this.repository = repository;
    }

    public List<ProjetEntity> findAll() {
        return repository.findAll();
    }

    public ProjetEntity findRequired(String id) throws ServiceFault {
        if (id == null || id.isBlank()) {
            throw ServiceFault.of("VALIDATION_ERROR", "L'identifiant du projet est obligatoire");
        }
        return repository.findById(id)
                .orElseThrow(() -> ServiceFault.of("NOT_FOUND", "Projet introuvable : " + id));
    }

    public ProjetEntity createProjet(ProjetInput input) throws ServiceFault {
        String nom = requireText(input.nom(), "Le nom du projet");
        String lang = requireText(input.lang(), "La langue du projet");
        String statut = input.statut() == null || input.statut().isBlank() ? "A_FAIRE" : input.statut().trim();

        return repository.save(new ProjetEntity("P-" + UUID.randomUUID().toString().substring(0, 8), nom, statut, lang));
    }

    public TacheEntity createTache(String projetId, TacheInput input) throws ServiceFault {
        ProjetEntity projet = findRequired(projetId);
        String titre = requireText(input.titre(), "Le titre de la tache");
        Integer tempsEstime = input.tempsEstime() == null ? 0 : input.tempsEstime();
        if (tempsEstime < 0) {
            throw ServiceFault.of("VALIDATION_ERROR", "Le temps estime ne peut pas etre negatif");
        }

        TacheEntity tache = new TacheEntity(
                "T-" + UUID.randomUUID().toString().substring(0, 8),
                titre,
                tempsEstime,
                defaultIfBlank(input.etat(), "A_FAIRE"),
                defaultIfBlank(input.priorite(), "MOYENNE"),
                defaultIfBlank(input.type(), "TACHE")
        );
        projet.addTache(tache);
        repository.save(projet);
        return tache;
    }

    public TacheEntity updateEtatTache(String projetId, String tacheId, String etat) throws ServiceFault {
        ProjetEntity projet = findRequired(projetId);
        String nouvelEtat = requireText(etat, "L'etat de la tache");

        TacheEntity tache = projet.findTache(tacheId)
                .orElseThrow(() -> ServiceFault.of("NOT_FOUND", "Tache introuvable : " + tacheId));
        tache.setEtat(nouvelEtat);
        repository.save(projet);
        return tache;
    }

    private static String requireText(String value, String label) throws ServiceFault {
        if (value == null || value.isBlank()) {
            throw ServiceFault.of("VALIDATION_ERROR", label + " est obligatoire");
        }
        return value.trim();
    }

    private static String defaultIfBlank(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value.trim();
    }
}
