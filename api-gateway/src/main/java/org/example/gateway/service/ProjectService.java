package org.example.gateway.service;

import org.example.gateway.rest.dto.CreateProjectRequest;
import org.example.gateway.rest.dto.CreateTaskRequest;
import org.example.gateway.rest.dto.ProjectDto;
import org.example.gateway.rest.dto.TaskDto;

import java.util.List;

/**
 * Contrat applicatif de la passerelle. Une future implementation REST/appel auth
 * pourra remplacer l'implementation SOAP sans toucher aux ressources JAX-RS.
 */
public interface ProjectService {

    List<ProjectDto> listProjects();

    ProjectDto getProject(String id);

    ProjectDto createProject(CreateProjectRequest request);

    ProjectDto createTask(String projectId, CreateTaskRequest request);

    TaskDto updateTaskState(String projectId, String taskId, String etat);
}
