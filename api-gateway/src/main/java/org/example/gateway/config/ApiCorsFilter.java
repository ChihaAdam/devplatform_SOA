package org.example.gateway.config;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

/** CORS ecrit directement dans les en-tetes : toutes les origines sont acceptees. */
@Provider
@PreMatching
public final class ApiCorsFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        if (!"OPTIONS".equalsIgnoreCase(request.getMethod())
                || request.getHeaderString("Origin") == null) {
            return;
        }
        // Preflight : on repond tout de suite, sans appeler la ressource REST.
        request.abortWith(Response.ok().build());
    }

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) {
        apply(response.getHeaders());
    }

    public static void apply(MultivaluedMap<String, Object> headers) {
        headers.putSingle("Access-Control-Allow-Origin", "*");
        headers.putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.putSingle("Access-Control-Allow-Headers", "*");
        headers.putSingle("Access-Control-Max-Age", "3600");
    }
}
