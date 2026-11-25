package org.example.gateway;

import org.example.gateway.rest.ApiException;
import org.example.gateway.soap.ServiceFault;
import org.example.gateway.soap.ServiceFaultBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiExceptionTest {

    @Test
    void translatesSoapFaultIntoHttpStatus() {
        assertEquals(404, fault("NOT_FOUND").status());
        assertEquals(400, fault("VALIDATION_ERROR").status());
    }

    private static ApiException fault(String code) {
        return ApiException.fromFault(new ServiceFault("message", new ServiceFaultBean(code, "message")));
    }
}
