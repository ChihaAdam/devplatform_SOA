package org.example.gateway.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import org.example.gateway.soap.ServiceFault;
import org.example.gateway.soap.model.DevPlatformSoap;
import org.example.gateway.soap.model.ProjetSoap;
import org.example.gateway.soap.model.TacheSoap;

@WebService(name = "ProjetPort", targetNamespace = SoapNames.NS)
public interface ProjetSoapPort {

    @WebMethod(operationName = "GetPlatform")
    @WebResult(name = "devPlatform")
    DevPlatformSoap getPlatform() throws ServiceFault;

    @WebMethod(operationName = "GetAllProjets")
    @WebResult(name = "projet")
    ProjetSoap[] getAllProjets() throws ServiceFault;

    @WebMethod(operationName = "GetProjet")
    @WebResult(name = "projet")
    ProjetSoap getProjet(@WebParam(name = "id") String id) throws ServiceFault;

    @WebMethod(operationName = "CreateProjet")
    @WebResult(name = "projet")
    ProjetSoap createProjet(@WebParam(name = "projet") ProjetSoap projet) throws ServiceFault;

    @WebMethod(operationName = "CreateTache")
    @WebResult(name = "projet")
    ProjetSoap createTache(@WebParam(name = "projetId") String projetId,
                           @WebParam(name = "tache") TacheSoap tache) throws ServiceFault;

    @WebMethod(operationName = "UpdateEtatTache")
    @WebResult(name = "tache")
    TacheSoap updateEtatTache(@WebParam(name = "projetId") String projetId,
                              @WebParam(name = "tacheId") String tacheId,
                              @WebParam(name = "etat") String etat) throws ServiceFault;

    @WebMethod(operationName = "GetOrganisation")
    @WebResult(name = "organisation")
    String getOrganisation() throws ServiceFault;
}
