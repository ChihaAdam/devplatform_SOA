package org.example.gateway.rest.dto;

public record CreateTaskRequest(String titre, Integer tempsEstime, String etat, String priorite, String type) {
}
