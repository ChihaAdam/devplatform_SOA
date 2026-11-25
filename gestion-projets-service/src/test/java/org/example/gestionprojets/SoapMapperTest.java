package org.example.gestionprojets;

import org.example.gestionprojets.domain.ProjetEntity;
import org.example.gestionprojets.domain.TacheEntity;
import org.example.gestionprojets.soap.SoapMapper;
import org.example.gestionprojets.soap.model.ProjetSoap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SoapMapperTest {

    @Test
    void mapsEntityToXsdShapedSoapObject() {
        ProjetEntity entity = new ProjetEntity("P-1", "Portail", "EN_COURS", "fr");
        entity.addTache(new TacheEntity("T-1", "Concevoir", 5, "A_FAIRE", "HAUTE", "TACHE"));

        ProjetSoap soap = SoapMapper.toSoap(entity);

        assertEquals("P-1", soap.getId());
        assertEquals("fr", soap.getLang());
        assertEquals("Portail", soap.getNom());
        assertEquals("EN_COURS", soap.getStatut());
        assertEquals(1, soap.getTaches().getTaches().length);
        assertEquals("Concevoir", soap.getTaches().getTaches()[0].getTitre());
    }
}
