package org.example.gateway;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.example.gateway.config.ApiCorsFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CorsFilterTest {

    @Test
    void headersAllowAnyOriginAndAnyHeader() {
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();

        ApiCorsFilter.apply(headers);

        assertEquals("*", headers.getFirst("Access-Control-Allow-Origin"));
        assertEquals("*", headers.getFirst("Access-Control-Allow-Headers"));
        assertEquals("GET, POST, PUT, DELETE, OPTIONS", headers.getFirst("Access-Control-Allow-Methods"));
    }
}
