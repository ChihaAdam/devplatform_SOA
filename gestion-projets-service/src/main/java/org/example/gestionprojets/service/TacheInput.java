package org.example.gestionprojets.service;

/** Commande metier independante du format SOAP. */
public record TacheInput(String titre, Integer tempsEstime, String etat, String priorite, String type) {
}
