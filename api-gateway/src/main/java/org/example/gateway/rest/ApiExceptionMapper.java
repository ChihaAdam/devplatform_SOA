package org.example.gateway.rest;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.example.gateway.rest.dto.ApiErrorBody;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {

    @Override
    public Response toResponse(ApiException exception) {
        return Response.status(exception.status())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ApiErrorBody(exception.code(), exception.getMessage()))
                .build();
    }
}
