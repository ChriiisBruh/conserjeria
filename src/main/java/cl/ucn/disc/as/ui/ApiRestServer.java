package cl.ucn.disc.as.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.javalin.Javalin;
import io.javalin.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.time.Instant;


/**
 * The server class
 * @author Christian San Juan
 */
@Slf4j
public final class ApiRestServer {
    /**
     * Nothing here
     */
    private ApiRestServer(){

    }

    /**
     *
     * @return the configured Gson
     */
    private static Gson createAndConfigureGson() {

        TypeAdapter<Instant> instantTypeAdapter = new TypeAdapter<Instant>() {
            /**
             * Writes one JSON value
             * @param out
             * @param instant the Java object to write. May be null.
             * @throws IOException
             */
            @Override
            public void write(JsonWriter out, Instant instant) throws IOException {
                if (instant == null) {
                    out.nullValue();
                } else {
                    out.value(instant.toEpochMilli());
                }
            }

            /**
             * Reads one JSON value
             * @param in
             * @return the converted Java object
             */
            @Override
            public Instant read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                return Instant.ofEpochMilli(in.nextLong());
            }
        };

        return new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Instant.class, instantTypeAdapter).create();
    }

    /**
     *
     * @return The configured Javalin Server
     */
    private static Javalin createAndConfigureJavalin(){

        JsonMapper jsonMapper = new JsonMapper() {


            private static final Gson GSON = createAndConfigureGson();

            /**
             * json to object
             */
            @NotNull
            @Override
            public <T> T fromJsonString(@NotNull String json, @NotNull Type targetType) {
                return GSON.fromJson(json,targetType);
            }

            /**
             * object to json
             */
            @NotNull
            @Override
            public String toJsonString(@NotNull Object obj, @NotNull Type type) {
                return GSON.toJson(obj, type);
            }
        };
        return Javalin.create(config -> {
            config.jsonMapper(jsonMapper);
            config.compression.gzipOnly(9);
            config.requestLogger.http((ctx, ms) -> {
                log.debug("served: {} in {} ms.", ctx.fullUrl(),ms);
            } );

            config.plugins.enableDevLogging();
        } );

    }

    /**
     * Starting server
     * @param port to use
     */
    public static Javalin start(final Integer port, final RoutesConfigurator routesConfigurator){

        if(port < 1024 || port > 65535){
            log.error("Bad port {}.", port);
            throw new IllegalArgumentException("Bad port: " + port);
        }

        log.debug("Starting api rest server in port {}...",port);

        Javalin app = createAndConfigureJavalin();

        routesConfigurator.configure(app);

        Runtime.getRuntime().addShutdownHook(new Thread(app::stop));

        app.events(event -> {
            event.serverStarting(() -> {
                log.debug("Starting the Javalin Server...");
            });
            event.serverStarted(() -> {
                log.debug("Server started");
            });
            event.serverStopping(() -> {
                log.debug("Server stopping");
            });
            event.serverStopped(() -> {
                log.debug("Server stopped");
            });

        });

        return app.start(port);
    }

}
