package org.example.gateway.rest.dto;

import java.util.List;

public record ProjectDto(String id,
                         String nom,
                         String statut,
                         String lang,
                         List<DeveloperDto> developpeurs,
                         List<TaskDto> taches) {
}
