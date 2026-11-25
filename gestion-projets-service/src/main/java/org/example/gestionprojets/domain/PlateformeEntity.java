package org.example.gestionprojets.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "plateforme")
public class PlateformeEntity {

    @Id
    @Column(name = "code", nullable = false, length = 32)
    private String code;

    @Column(name = "organisation", nullable = false)
    private String organisation;

    public PlateformeEntity() {
    }

    public PlateformeEntity(String code, String organisation) {
        this.code = code;
        this.organisation = organisation;
    }

    public String getCode() {
        return code;
    }

    public String getOrganisation() {
        return organisation;
    }
}
