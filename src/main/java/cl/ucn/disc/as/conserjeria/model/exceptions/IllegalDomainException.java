package cl.ucn.disc.as.conserjeria.model.exceptions;
/**
 * Domain Violation Exception
 * @author Christian San Juan
 */
public class IllegalDomainException extends RuntimeException {

    /**
     * The Constructor
     *
     * @param message to use
     */
    public IllegalDomainException(String message) {
        super(message);
    }
}
