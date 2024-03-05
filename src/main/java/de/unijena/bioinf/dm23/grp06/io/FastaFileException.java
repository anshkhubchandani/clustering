package de.unijena.bioinf.dm23.grp06.io;
/**
 * Creates a new FastaFileException
 */
public class FastaFileException extends RuntimeException {
    public FastaFileException() {super("Error: Your Fasta seems to be incorrect or corrupted.");}
}