package cl.ucn.disc.as.services;

import cl.ucn.disc.as.model.*;
import io.ebean.Database;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.persistence.PersistenceException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SistemaImpl implements Sistema{
    /**
     * The database
     */
    private final Database database;

    public SistemaImpl(Database database) {
        this.database = database;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Edificio add(@NotNull Edificio edificio){
        try {
            this.database.save(edificio);
        } catch (PersistenceException ex){
            log.error("Error al agregar un edificio: " + ex.getMessage(), ex);
            throw new SecurityException("Error al agregar un edificio",ex);

        }
        return edificio;
    }

    @Override
    public Persona add(Persona persona) {
        try {
            this.database.save(persona);
        } catch (PersistenceException ex) {
            log.error("Error al agregar una persona: " + ex.getMessage(), ex);
            throw new SecurityException("Error al agregar una persona", ex);
        }

        return persona;
    }

    @Override
    public Departamento addDepartamento(Departamento departamento, Edificio edificio) {
        edificio.addDepartamento(departamento);
        try {
            this.database.save(departamento);
        } catch (PersistenceException ex) {
            log.error("Error al agregar un departamento: " + ex.getMessage(), ex);
            throw new SecurityException("Error al agregar un departamento", ex);
        }
        return departamento;
    }

    @Override
    public Departamento addDepartamento(Departamento departamento, Long idEdificio) {
        Edificio edificio = this.database.find(Edificio.class, idEdificio);
        if (edificio == null) {
            throw new IllegalArgumentException("El edificio con ID " + idEdificio + " no existe.");
        }
        return addDepartamento(departamento, edificio);
    }
    @Override
    public Contrato realizarContrato(@NotNull Persona persona, @NotNull Departamento departamento, @NotNull Instant fechaPago) {
        Contrato contrato = Contrato.builder()
                .fechaPago(fechaPago)
                .persona(persona)
                .departamento(departamento)
                .pagos(new ArrayList<>())
                .build();

        try {
            this.database.save(contrato);
        } catch (PersistenceException ex) {
            log.error("Error al realizar un contrato: " + ex.getMessage(), ex);
            throw new SecurityException("Error al realizar un contrato", ex);
        }

        return contrato;
    }

    @Override
    public Contrato realizarContrato(@NotNull Long idDuenio, @NotNull Long idDepartamento, @NotNull Instant fechaPago) {
        Persona duenio = this.database.find(Persona.class, idDuenio);
        Departamento departamento = this.database.find(Departamento.class, idDepartamento);
        if (duenio == null || departamento == null) {
            throw new IllegalArgumentException("La persona o el departamento especificado no existen.");
        }
        return realizarContrato(duenio, departamento, fechaPago);
    }

    @Override
    public List<Contrato> getContratos() {
        return this.database.find(Contrato.class).findList();
    }

    @Override
    public List<Persona> getPersonas() {
        return this.database.find(Persona.class).findList();
    }

    @Override
    public List<Pago> getPagos(@NotNull String rut) {
        return this.database.find(Pago.class).where().eq("persona.rut", rut).findList();
    }
}
