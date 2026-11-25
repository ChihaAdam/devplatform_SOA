package org.example.gateway.service;

import jakarta.xml.ws.WebServiceException;
import org.example.gateway.rest.ApiException;
import org.example.gateway.rest.dto.CreateProjectRequest;
import org.example.gateway.rest.dto.CreateTaskRequest;
import org.example.gateway.rest.dto.DeveloperDto;
import org.example.gateway.rest.dto.ProjectDto;
import org.example.gateway.rest.dto.TaskDto;
import org.example.gateway.soap.ProjectsSoapClient;
import org.example.gateway.soap.ProjetSoapPort;
import org.example.gateway.soap.ServiceFault;
import org.example.gateway.soap.model.DeveloppeurSoap;
import org.example.gateway.soap.model.ProjetSoap;
import org.example.gateway.soap.model.TacheSoap;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoapProjectService implements ProjectService {

    private static final Logger log = Logger.getLogger(SoapProjectService.class.getName());

    private final ProjectsSoapClient client;

    public SoapProjectService(ProjectsSoapClient client) {
        this.client = client;
    }

    @Override
    public List<ProjectDto> listProjects() {
        ProjetSoap[] projets;
        try {
            projets = port().getAllProjets();
        } catch (ServiceFault fault) {
            throw ApiException.fromFault(fault);
        } catch (WebServiceException e) {
            throw unavailable(e);
        }
        return Arrays.stream(projets).map(SoapProjectService::toDto).toList();
    }

    @Override
    public ProjectDto getProject(String id) {
        if (id == null || id.isBlank()) {
            throw ApiException.badRequest("L'identifiant du projet est obligatoire");
        }
        try {
            return toDto(port().getProjet(id));
        } catch (ServiceFault fault) {
            throw ApiException.fromFault(fault);
        } catch (WebServiceException e) {
            throw unavailable(e);
        }
    }

    @Override
    public ProjectDto createProject(CreateProjectRequest request) {
        if (request == null || request.nom() == null || request.nom().isBlank()) {
            throw ApiException.badRequest("Le nom du projet est obligatoire");
        }
        if (request.lang() == null || request.lang().isBlank()) {
            throw ApiException.badRequest("La langue du projet est obligatoire");
        }

        ProjetSoap projet = new ProjetSoap();
        projet.setNom(request.nom());
        projet.setStatut(request.statut());
        projet.setLang(request.lang());

        try {
            return toDto(port().createProjet(projet));
        } catch (ServiceFault fault) {
            throw ApiException.fromFault(fault);
        } catch (WebServiceException e) {
            throw unavailable(e);
        }
    }

    @Override
    public ProjectDto createTask(String projectId, CreateTaskRequest request) {
        if (request == null || request.titre() == null || request.titre().isBlank()) {
            throw ApiException.badRequest("Le titre de la tache est obligatoire");
        }

        TacheSoap tache = new TacheSoap();
        tache.setTitre(request.titre());
        tache.setTempsEstime(request.tempsEstime());
        tache.setEtat(request.etat());
        tache.setPriorite(request.priorite());
        tache.setType(request.type());

        try {
            return toDto(port().createTache(projectId, tache));
        } catch (ServiceFault fault) {
            throw ApiException.fromFault(fault);
        } catch (WebServiceException e) {
            throw unavailable(e);
        }
    }

    @Override
    public TaskDto updateTaskState(String projectId, String taskId, String etat) {
        if (etat == null || etat.isBlank()) {
            throw ApiException.badRequest("L'etat de la tache est obligatoire");
        }
        try {
            return toDto(port().updateEtatTache(projectId, taskId, etat));
        } catch (ServiceFault fault) {
            throw ApiException.fromFault(fault);
        } catch (WebServiceException e) {
            throw unavailable(e);
        }
    }

    private ProjetSoapPort port() {
        return client.port();
    }

    private static ApiException unavailable(WebServiceException exception) {
        log.log(Level.WARNING, "Appel SOAP impossible : " + exception.getMessage(), exception);
        return ApiException.soapUnavailable(exception);
    }

    private static ProjectDto toDto(ProjetSoap soap) {
        DeveloppeurSoap[] developpeurs = soap.getEquipe() == null
                ? new DeveloppeurSoap[0]
                : soap.getEquipe().getDeveloppeurs();
        TacheSoap[] taches = soap.getTaches() == null
                ? new TacheSoap[0]
                : soap.getTaches().getTaches();

        List<DeveloperDto> members = Arrays.stream(developpeurs)
                .map(d -> new DeveloperDto(d.getId(), d.getNom(), d.getEmail(), d.getRole()))
                .toList();
        List<TaskDto> items = Arrays.stream(taches).map(SoapProjectService::toDto).toList();

        return new ProjectDto(soap.getId(), soap.getNom(), soap.getStatut(), soap.getLang(), members, items);
    }

    private static TaskDto toDto(TacheSoap soap) {
        return new TaskDto(soap.getId(), soap.getTitre(), soap.getTempsEstime(),
                soap.getEtat(), soap.getPriorite(), soap.getType());
    }
}
