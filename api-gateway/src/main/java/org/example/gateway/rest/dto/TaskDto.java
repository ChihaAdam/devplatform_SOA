package org.example.gateway.rest.dto;

public record TaskDto(String id, String titre, Integer tempsEstime, String etat, String priorite, String type) {
}
