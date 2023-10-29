package cl.ucn.disc.as;

import cl.ucn.disc.as.dao.PersonaFinder;
import cl.ucn.disc.as.model.Edificio;
import cl.ucn.disc.as.model.Persona;
import cl.ucn.disc.as.services.Sistema;
import cl.ucn.disc.as.services.SistemaImpl;
import cl.ucn.disc.as.ui.ApiRestServer;
import cl.ucn.disc.as.ui.WebController;
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

        ApiRestServer.start(7070, new WebController());

        log.debug("Done :* ");

    }
}