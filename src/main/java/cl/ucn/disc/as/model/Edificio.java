/*
 * Copyright (c) 2023. Arquitectura de Sistemas, DISC, UCN.
 */

package cl.ucn.disc.as.model;

import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * The Persona class.
 *
 * @author Christian San Juan
 */
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Edificio extends BaseModel{
    /**
     * The Nombre.
     */
    @NotNull
    @Getter
    private String nombre;

    /**
     * The Direccion.
     */
    @NotNull
    @Getter
    private String direccion;

    /**
     * The nPisos.
     */
    @NotNull
    @Getter
    private Integer nPisos;

    /**
     * The departamentos
     */
    @NotNull
    @Getter
    private List<Departamento> departamentos = new ArrayList<>();

    /**
     * Method addDepartamento
     * @param departamento
     */
    public void addDepartamento(Departamento departamento) {
        departamentos.add(departamento);
    }
}
