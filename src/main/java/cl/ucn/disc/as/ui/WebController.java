package cl.ucn.disc.as.ui;

import cl.ucn.disc.as.model.Persona;
import cl.ucn.disc.as.services.Sistema;
import cl.ucn.disc.as.services.SistemaImpl;
import io.ebean.DB;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;

import java.util.Optional;

public final class WebController implements RoutesConfigurator {

    /**
     * The sistema
     */
    private final Sistema sistema;

    /**
     * The web controller
     */
    public WebController() {
        this.sistema = new SistemaImpl(DB.getDefault());
        this.sistema.populate();
    }

    @Override
    public void configure(final Javalin app) {
        app.get("/",ctx -> {
            ctx.result("Welcome to Conserjeria API REST");
        });

        app.get("/personas/rut/{rut}",ctx -> {
            String rut = ctx.pathParam("rut");
            Optional<Persona> oPersona = this.sistema.getPersona(rut);
            ctx.json(oPersona.orElseThrow(() -> new NotFoundResponse("Cant find persona with rut"+rut)));
        });
    }
}
