package edu.ecu.cs.csci6030.search;

public class InvalidDirectoryException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Create a new InvalidDirectoryException.
     *
     * @param msg the exception message
     */
    public InvalidDirectoryException(String msg) {
        super(msg);
    }

}
