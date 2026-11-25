package org.example.gestionprojets.service;

/** Commande metier independante du format SOAP. */
public record ProjetInput(String nom, String statut, String lang) {
}
