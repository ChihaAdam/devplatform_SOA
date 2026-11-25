package org.example.gateway;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.gateway.rest.WebApplicationExceptionMapper;
import org.example.gateway.rest.dto.ApiErrorBody;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WebApplicationExceptionMapperTest {

    private final WebApplicationExceptionMapper mapper = new WebApplicationExceptionMapper();

    @Test
    void keepsHttpStatusForUnmatchedRoute() {
        Response response = mapper.toResponse(new NotFoundException());

        assertEquals(404, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
        assertEquals(new ApiErrorBody("NOT_FOUND", "Ressource introuvable"), response.getEntity());
    }
}
