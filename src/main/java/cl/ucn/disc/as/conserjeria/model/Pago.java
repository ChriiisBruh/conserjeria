package cl.ucn.disc.as.conserjeria.model;
import io.ebean.annotation.NotNull;
import lombok.Getter;
import javax.persistence.Entity;
import java.time.Instant;

@Entity
public class Pago {
    /**
     * The fechaPago
     */
    @Getter
    @NotNull
    private Instant fechaPago;

    /**
     * The monto
     */
    @NotNull
    @Getter
    private Integer monto;

}
