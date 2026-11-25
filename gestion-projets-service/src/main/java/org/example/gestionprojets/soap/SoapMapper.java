package org.example.gestionprojets.soap;

import org.example.gestionprojets.domain.DeveloppeurEntity;
import org.example.gestionprojets.domain.ProjetEntity;
import org.example.gestionprojets.domain.TacheEntity;
import org.example.gestionprojets.service.ProjetInput;
import org.example.gestionprojets.service.TacheInput;
import org.example.gestionprojets.soap.model.DeveloppeurSoap;
import org.example.gestionprojets.soap.model.EquipeSoap;
import org.example.gestionprojets.soap.model.ProjetSoap;
import org.example.gestionprojets.soap.model.TacheSoap;
import org.example.gestionprojets.soap.model.TachesSoap;

/** Traduction entre les entites JPA et les objets SOAP qui suivent devplatform.xsd. */
public final class SoapMapper {

    private SoapMapper() {
    }

    public static ProjetSoap toSoap(ProjetEntity entity) {
        ProjetSoap projet = new ProjetSoap();
        projet.setId(entity.getId());
        projet.setNom(entity.getNom());
        projet.setStatut(entity.getStatut());
        projet.setLang(entity.getLang());

        DeveloppeurSoap[] developpeurs = entity.getDeveloppeurs().stream()
                .map(SoapMapper::toSoap)
                .toArray(DeveloppeurSoap[]::new);
        TacheSoap[] taches = entity.getTaches().stream()
                .map(SoapMapper::toSoap)
                .toArray(TacheSoap[]::new);

        projet.setEquipe(new EquipeSoap(developpeurs));
        projet.setTaches(new TachesSoap(taches));
        return projet;
    }

    public static DeveloppeurSoap toSoap(DeveloppeurEntity entity) {
        return new DeveloppeurSoap(entity.getId(), entity.getNom(), entity.getEmail(), entity.getRole());
    }

    public static TacheSoap toSoap(TacheEntity entity) {
        return new TacheSoap(
                entity.getId(),
                entity.getTitre(),
                entity.getTempsEstime(),
                entity.getEtat(),
                entity.getPriorite(),
                entity.getType()
        );
    }

    public static ProjetInput toInput(ProjetSoap soap) {
        if (soap == null) {
            return new ProjetInput(null, null, null);
        }
        return new ProjetInput(soap.getNom(), soap.getStatut(), soap.getLang());
    }

    public static TacheInput toInput(TacheSoap soap) {
        if (soap == null) {
            return new TacheInput(null, null, null, null, null);
        }
        return new TacheInput(
                soap.getTitre(),
                soap.getTempsEstime(),
                soap.getEtat(),
                soap.getPriorite(),
                soap.getType()
        );
    }
}
