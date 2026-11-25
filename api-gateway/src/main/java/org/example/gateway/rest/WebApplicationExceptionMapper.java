package org.example.gateway.rest;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.example.gateway.rest.dto.ApiErrorBody;

/** Erreurs HTTP de Jersey (404, 405, ...) : on garde le statut et on renvoie du JSON. */
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {
        int status = exception.getResponse().getStatus();
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ApiErrorBody(code(status), message(status)))
                .build();
    }

    private static String code(int status) {
        return switch (status) {
            case 400 -> "VALIDATION_ERROR";
            case 404 -> "NOT_FOUND";
            case 405 -> "METHOD_NOT_ALLOWED";
            case 415 -> "UNSUPPORTED_MEDIA_TYPE";
            default -> "HTTP_ERROR";
        };
    }

    private static String message(int status) {
        return switch (status) {
            case 400 -> "Requete invalide";
            case 404 -> "Ressource introuvable";
            case 405 -> "Methode non autorisee";
            case 415 -> "Type de contenu non pris en charge";
            default -> "Erreur HTTP " + status;
        };
    }
}
