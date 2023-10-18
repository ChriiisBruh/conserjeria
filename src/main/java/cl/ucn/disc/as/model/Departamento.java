/*
 * Copyright (c) 2023. Arquitectura de Sistemas, DISC, UCN.
 */

package cl.ucn.disc.as.model;

import io.ebean.annotation.NotNull;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Builder
public class Departamento extends BaseModel{
    /**
     * The Numero.
     */
    @NotNull
    @Getter
    private Integer numero;

    /**
     * The nPiso.
     */
    @NotNull
    @Getter
    private Integer nPiso;
}
