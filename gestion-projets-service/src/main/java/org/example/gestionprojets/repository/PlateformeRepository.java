package org.example.gestionprojets.repository;

import org.example.gestionprojets.domain.PlateformeEntity;
import org.example.gestionprojets.persistence.Persistence;

import java.util.Optional;

public final class PlateformeRepository {

    public Optional<PlateformeEntity> findById(String code) {
        return Persistence.read(em -> Optional.ofNullable(em.find(PlateformeEntity.class, code)));
    }

    public boolean existsById(String code) {
        return findById(code).isPresent();
    }

    public void save(PlateformeEntity plateforme) {
        Persistence.write(em -> {
            em.persist(plateforme);
            return plateforme;
        });
    }
}
