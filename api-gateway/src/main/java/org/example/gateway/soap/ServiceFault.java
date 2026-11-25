package org.example.gateway.soap;

import jakarta.xml.ws.WebFault;

/** Fault SOAP recu du service metier. */
@WebFault(
        name = "ServiceFault",
        targetNamespace = SoapNames.NS,
        faultBean = "org.example.gateway.soap.ServiceFaultBean"
)
public class ServiceFault extends Exception {

    private final ServiceFaultBean faultInfo;

    public ServiceFault(String message, ServiceFaultBean faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public ServiceFaultBean getFaultInfo() {
        return faultInfo;
    }

    public String getCode() {
        return faultInfo == null ? null : faultInfo.getCode();
    }

    @Override
    public String getMessage() {
        return faultInfo == null ? super.getMessage() : faultInfo.getMessage();
    }
}
