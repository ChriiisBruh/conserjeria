package cl.ucn.disc.as.conserjeria.model;
import java.time.Instant;
import java.util.List;
import io.ebean.annotation.NotNull;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Builder
public class Contrato extends BaseModel{
    /**
     * The fechaPago
     */
    @NotNull
    private Instant fechaPago;

    /**
     * The persona
     */
    @ManyToOne
    @Getter
    @NotNull

    private Persona persona;

    /**
     * The departamento
     */
    @ManyToOne
    @Getter
    @NotNull

    private Departamento departamento;
    /**
     * The pagos
     */
    @OneToMany(cascade = CascadeType.ALL)
    @Getter

    private List<Pago> pagos;

}