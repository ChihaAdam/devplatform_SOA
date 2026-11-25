package org.example.gestionprojets.service;

import jakarta.xml.ws.WebFault;
import org.example.gestionprojets.soap.SoapNames;

/** Exception fonctionnelle traduite en SOAP Fault, puis en erreur JSON par la gateway. */
@WebFault(
        name = "ServiceFault",
        targetNamespace = SoapNames.NS,
        faultBean = "org.example.gestionprojets.service.ServiceFaultBean"
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

    public static ServiceFault of(String code, String message) {
        return new ServiceFault(message, new ServiceFaultBean(code, message));
    }
}
