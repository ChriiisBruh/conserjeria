/*
 * Copyright (c) 2023. Arquitectura de Sistemas, DISC, UCN.
 */

package cl.ucn.disc.as.conserjeria.model;

import cl.ucn.disc.as.conserjeria.model.exceptions.IllegalDomainException;
import cl.ucn.disc.as.conserjeria.utils.ValidationUtils;
import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * The Persona class.
 *
 * @author Diego Urrutia-Astorga.
 */
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Persona extends BaseModel {

    /**
     * The RUT.
     */
    @NotNull
    @Getter
    private String rut;

    /**
     * The Nombre.
     */
    @NotNull
    @Getter
    private String nombre;

    /**
     * The Apellidos.
     */
    @NotNull
    @Getter
    private String apellidos;

    /**
     * The Email.
     */
    @NotNull
    @Getter
    private String email;

    /**
     * The Telefono.
     */
    @NotNull
    @Getter
    private String telefono;

    /**
     * Custom builder to validate
     */
    public static class PersonaBuilder {
        /**
         * @return the Persona.
         */
        public Persona build() {
            // validate the rut
            if(!ValidationUtils.isRutValid((this.rut))){
                throw new IllegalDomainException(("Rut no válido: " +this.rut));
            }
            // validate the email
            if(!ValidationUtils.isEmailValid((this.email))){
                throw new IllegalDomainException(("Email no válido: " +this.email));
            }
            // validate the telefono
            if(!ValidationUtils.isTelefonoValid((this.telefono))){
                throw new IllegalDomainException(("Telefono no valido: " +this.telefono));
            }
            // validate the nombre
            if(!ValidationUtils.isNombreValid((this.nombre))){
                throw new IllegalDomainException(("Nombre no valido: " +this.nombre));
            }
            // validate the apellidos
            if(!ValidationUtils.isApellidosValid((this.apellidos))){
                throw new IllegalDomainException(("Apellido no valido: " +this.apellidos));
            }

            return new Persona(this.rut, this.nombre, this.apellidos, this.email, this.telefono);
        }
    }

}
