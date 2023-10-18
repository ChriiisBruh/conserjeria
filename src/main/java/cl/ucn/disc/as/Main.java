package cl.ucn.disc.as;

import cl.ucn.disc.as.dao.PersonaFinder;
import cl.ucn.disc.as.model.Edificio;
import cl.ucn.disc.as.model.Persona;
import cl.ucn.disc.as.services.Sistema;
import cl.ucn.disc.as.services.SistemaImpl;
import io.ebean.DB;
import io.ebean.Database;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

/**
 * The Main
 *
 * @author Christian San Juan
 */
@Slf4j
public final class Main {
    /**
     * The Main
     *
     * @param args
     */
    public static void main(String[] args) {
        log.debug("Starting Main ...");

        //get the database
        Database db = DB.getDefault();

        //the sistema
        Sistema sistema = new SistemaImpl(db);

        Edificio edificio = Edificio.builder()
                .nombre("Y1")
                .direccion("Angamos #0610 ")
                .nPisos(10)
                .build();
        log.debug("Edificio after db: {}", edificio);

        edificio = sistema.add(edificio);
        log.debug("Edificio before db: {}",edificio);

        // build the persona
        Persona persona = Persona.builder()
                .rut("21029218-7")
                .nombre("Christian")
                .apellidos("San Juan Lira")
                .email("chris@gmail.com")
                .telefono("+56912345678")
                .build();

        //save into db
        db.save(persona);
        log.debug("The persona: ${} ", persona);

        //finder de personas
        PersonaFinder pf = new PersonaFinder();
        Optional<Persona> oPersona = pf.byRut("21029218-7");
        oPersona.ifPresent(p -> log.debug("Persona from db: {}", p));
        log.debug("Listo. ");
    }
}