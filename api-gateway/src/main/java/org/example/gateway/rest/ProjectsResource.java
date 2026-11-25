package org.example.gateway.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.gateway.rest.dto.CreateProjectRequest;
import org.example.gateway.rest.dto.CreateTaskRequest;
import org.example.gateway.rest.dto.ProjectDto;
import org.example.gateway.rest.dto.TaskDto;
import org.example.gateway.rest.dto.TaskStateRequest;
import org.example.gateway.service.ProjectService;

import java.net.URI;
import java.util.List;

@Path("/api/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectsResource {

    private final ProjectService projectService;

    @Inject
    public ProjectsResource(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GET
    public List<ProjectDto> list() {
        return projectService.listProjects();
    }

    @GET
    @Path("{id}")
    public ProjectDto get(@PathParam("id") String id) {
        return projectService.getProject(id);
    }

    @POST
    public Response create(CreateProjectRequest request) {
        ProjectDto created = projectService.createProject(request);
        return Response.created(URI.create("/api/projects/" + created.id())).entity(created).build();
    }

    @POST
    @Path("{id}/tasks")
    public Response createTask(@PathParam("id") String id, CreateTaskRequest request) {
        ProjectDto updated = projectService.createTask(id, request);
        return Response.created(URI.create("/api/projects/" + id)).entity(updated).build();
    }

    @PUT
    @Path("{id}/tasks/{taskId}")
    public TaskDto updateTaskState(@PathParam("id") String id,
                                   @PathParam("taskId") String taskId,
                                   TaskStateRequest request) {
        String etat = request == null ? null : request.etat();
        return projectService.updateTaskState(id, taskId, etat);
    }
}
