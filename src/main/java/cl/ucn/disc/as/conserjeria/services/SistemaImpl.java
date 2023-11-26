package cl.ucn.disc.as.conserjeria.services;

import cl.ucn.disc.as.conserjeria.model.*;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.ebean.Database;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.persistence.PersistenceException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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

    @Override
    public Optional<Persona> getPersona(String rut) {
        Persona persona = this.database.find(Persona.class).where().eq("rut", rut).findOne();
        return Optional.ofNullable(persona);
    }

    @Override
    public void populate() {
        {
            Persona persona1 = Persona.builder()
                    .rut("20725077-5")
                    .nombre("Juan")
                    .apellidos("Perez")
                    .email("juan@example.com")
                    .telefono("123456789")
                    .build();

            Persona persona2 = Persona.builder()
                    .rut("21029218-7")
                    .nombre("Maria")
                    .apellidos("Gonzalez")
                    .email("maria@example.com")
                    .telefono("987654321")
                    .build();

            this.database.save(persona1);
            this.database.save(persona2);
        }
        Locale locale = new Locale("es-CL");
        FakeValuesService fvs = new FakeValuesService(locale, new RandomService());
        Faker faker = new Faker(locale);

        //faker
        for(int i = 0; i<1000;i++){
            Persona persona = Persona.builder()
                    .rut(fvs.bothify("########-#"))
                    .nombre(faker.name()
                            .firstName())
                    .apellidos(faker.name()
                            .lastName())
                    .email(fvs.bothify("????##@gmail.com"))
                    .telefono(fvs.bothify("+569########"))
                    .build();
            this.database.save(persona);

        }





        log.info("Base de datos poblada con datos de ejemplo.");
    }
}
