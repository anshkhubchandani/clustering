package de.unijena.bioinf.dm23.clustering.algo;
/**
 * The MatrixNotConnectedException is thrown when a matrix is found to be not connected.
 */
public class MatrixNotConnectedException extends Exception {
    /**
     * Constructs a new MatrixNotConnectedException with the specified detail message.
     *
     * @param message the detail message
     */
    public MatrixNotConnectedException(String message) {
        super(message);
    }
}