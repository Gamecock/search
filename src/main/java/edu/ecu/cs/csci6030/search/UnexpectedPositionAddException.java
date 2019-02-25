package edu.ecu.cs.csci6030.search;

/**
 * Exception if word added out of order
 *
 * @author Mike Finch
 * @version 1.0
 *
 */
public class UnexpectedPositionAddException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Create a new NameNotDefinedException.
     *
     * @param msg the exception message
     */
    public UnexpectedPositionAddException(String msg) {
        super(msg);
    }

}
