package org.example.gateway.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/** Detail du SOAP Fault cote client, mime namespace que le service. */
@XmlRootElement(name = "ServiceFault", namespace = SoapNames.NS)
@XmlType(name = "ServiceFault", namespace = SoapNames.NS)
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceFaultBean {

    @XmlElement(required = true)
    private String code;

    @XmlElement(required = true)
    private String message;

    public ServiceFaultBean() {
    }

    public ServiceFaultBean(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
