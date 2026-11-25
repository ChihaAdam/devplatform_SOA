package org.example.gestionprojets.repository;

import org.example.gestionprojets.domain.ProjetEntity;
import org.example.gestionprojets.persistence.Persistence;

import java.util.List;
import java.util.Optional;

/** Acces JPA aux projets. Les collections sont chargees avec l'entite, hors session fermee. */
public final class ProjetRepository {

    private static final String FETCH =
            "select distinct p from ProjetEntity p "
                    + "left join fetch p.developpeurs "
                    + "left join fetch p.taches";

    public List<ProjetEntity> findAll() {
        return Persistence.read(em -> em.createQuery(FETCH + " order by p.id", ProjetEntity.class)
                .getResultList());
    }

    public Optional<ProjetEntity> findById(String id) {
        return Persistence.read(em -> em.createQuery(FETCH + " where p.id = :id", ProjetEntity.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst());
    }

    public long count() {
        return Persistence.read(em -> em.createQuery(
                "select count(p) from ProjetEntity p", Long.class).getSingleResult());
    }

    public ProjetEntity save(ProjetEntity projet) {
        return Persistence.write(em -> {
            if (em.find(ProjetEntity.class, projet.getId()) == null) {
                em.persist(projet);
                return projet;
            }
            return em.merge(projet);
        });
    }
}
