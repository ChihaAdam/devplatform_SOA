package org.example.gateway.rest;

import org.example.gateway.soap.ServiceFault;

/** Erreur applicative de la passerelle : code stable pour le front, message lisible. */
public class ApiException extends RuntimeException {

    private final int status;
    private final String code;

    public ApiException(int status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public static ApiException badRequest(String message) {
        return new ApiException(400, "VALIDATION_ERROR", message);
    }

    public static ApiException notFound(String message) {
        return new ApiException(404, "NOT_FOUND", message);
    }

    public static ApiException fromFault(ServiceFault fault) {
        int status = switch (fault.getCode() == null ? "" : fault.getCode()) {
            case "NOT_FOUND" -> 404;
            case "VALIDATION_ERROR" -> 400;
            default -> 500;
        };
        String code = fault.getCode() == null ? "SERVICE_ERROR" : fault.getCode();
        return new ApiException(status, code, fault.getMessage());
    }

    public static ApiException soapUnavailable(Throwable cause) {
        return new ApiException(502, "SERVICE_UNAVAILABLE",
                "Le service de gestion des projets est indisponible", cause);
    }

    private ApiException(int status, String code, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.code = code;
    }

    public int status() {
        return status;
    }

    public String code() {
        return code;
    }
}
