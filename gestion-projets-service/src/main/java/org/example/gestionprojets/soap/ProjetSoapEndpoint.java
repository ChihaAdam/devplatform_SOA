package org.example.gestionprojets.soap;

import jakarta.jws.WebService;
import org.example.gestionprojets.domain.ProjetEntity;
import org.example.gestionprojets.repository.PlateformeRepository;
import org.example.gestionprojets.service.ProjetService;
import org.example.gestionprojets.service.ServiceFault;
import org.example.gestionprojets.soap.model.DevPlatformSoap;
import org.example.gestionprojets.soap.model.ProjetSoap;
import org.example.gestionprojets.soap.model.ProjetsSoap;
import org.example.gestionprojets.soap.model.TacheSoap;

import java.util.List;

@WebService(
        endpointInterface = "org.example.gestionprojets.soap.ProjetSoapPort",
        serviceName = "ProjetService",
        portName = "ProjetPort",
        targetNamespace = SoapNames.NS
)
public class ProjetSoapEndpoint implements ProjetSoapPort {

    private final ProjetService projetService;
    private final PlateformeRepository plateformes;
    private final String organisationParDefaut;

    public ProjetSoapEndpoint(ProjetService projetService,
                              PlateformeRepository plateformes,
                              String organisationParDefaut) {
        this.projetService = projetService;
        this.plateformes = plateformes;
        this.organisationParDefaut = organisationParDefaut;
    }

    @Override
    public DevPlatformSoap getPlatform() throws ServiceFault {
        ProjetSoap[] projets = getAllProjets();
        return new DevPlatformSoap(getOrganisation(), new ProjetsSoap(projets));
    }

    @Override
    public ProjetSoap[] getAllProjets() {
        List<ProjetEntity> entites = projetService.findAll();
        return entites.stream().map(SoapMapper::toSoap).toArray(ProjetSoap[]::new);
    }

    @Override
    public ProjetSoap getProjet(String id) throws ServiceFault {
        return SoapMapper.toSoap(projetService.findRequired(id));
    }

    @Override
    public ProjetSoap createProjet(ProjetSoap projet) throws ServiceFault {
        return SoapMapper.toSoap(projetService.createProjet(SoapMapper.toInput(projet)));
    }

    @Override
    public ProjetSoap createTache(String projetId, TacheSoap tache) throws ServiceFault {
        projetService.createTache(projetId, SoapMapper.toInput(tache));
        return SoapMapper.toSoap(projetService.findRequired(projetId));
    }

    @Override
    public TacheSoap updateEtatTache(String projetId, String tacheId, String etat) throws ServiceFault {
        return SoapMapper.toSoap(projetService.updateEtatTache(projetId, tacheId, etat));
    }

    @Override
    public String getOrganisation() {
        return plateformes.findById("DEFAULT")
                .map(plateforme -> plateforme.getOrganisation())
                .orElse(organisationParDefaut);
    }
}
