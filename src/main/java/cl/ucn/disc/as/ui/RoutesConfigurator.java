package cl.ucn.disc.as.ui;

import io.javalin.Javalin;

/**
 * The routes config.
 *
 * @author Christian San Juan
 */
public interface RoutesConfigurator {

    /**
     * Configure the routes
     * @param javalin to use.
     */
    void configure(Javalin javalin);

}
