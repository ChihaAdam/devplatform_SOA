package org.example.gateway.rest;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.example.gateway.rest.dto.ApiErrorBody;

import java.util.logging.Level;
import java.util.logging.Logger;

/** Dernier rempart : aucune stacktrace ni detail technique ne part vers le navigateur. */
@Provider
public class UnexpectedExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger log = Logger.getLogger(UnexpectedExceptionMapper.class.getName());

    @Override
    public Response toResponse(Exception exception) {
        log.log(Level.SEVERE, "Erreur non traitee dans la passerelle", exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ApiErrorBody("INTERNAL_ERROR", "Une erreur inattendue est survenue"))
                .build();
    }
}
