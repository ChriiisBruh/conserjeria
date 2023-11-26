package cl.ucn.disc.as;

import cl.ucn.disc.as.conserjeria.grpc.PersonaGrpcServiceImpl;
import cl.ucn.disc.as.conserjeria.ui.ApiRestServer;
import cl.ucn.disc.as.conserjeria.ui.WebController;
import io.javalin.Javalin;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

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
    public static void main(String[] args) throws InterruptedException, IOException {
        log.debug("Starting Main ...");

        log.debug("Library path {}", System.getProperty("java.library.path"));

        log.debug("Starting ApiRest server ..");

        Javalin app = ApiRestServer.start(7070,new WebController());

        //log.debug("Stopping");

        //app.stop();

        log.debug("Starting the gRPC server ..");
        Server server = ServerBuilder
                .forPort(50123)
                .addService(new PersonaGrpcServiceImpl())
                .build();
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));

        server.awaitTermination();

        log.debug("Done :* ");

    }
}