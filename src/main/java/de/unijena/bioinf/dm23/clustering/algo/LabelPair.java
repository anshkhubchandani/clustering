package de.unijena.bioinf.dm23.clustering.algo;

import java.util.Objects;
/**
 * The LabelPair class represents a pair of labels.
 */
public class LabelPair {

    String first;
    String second;

    /**
     * Constructs a new LabelPair object with the specified labels.
     *
     * @param firstTaxon  the first label
     * @param secondTaxon the second label
     */

    public LabelPair(String firstTaxon, String secondTaxon) {
        this.first = firstTaxon;
        this.second = secondTaxon;
    }

    /**
     * Checks if the label pair contains a specific taxon.
     *
     * @param taxonToCompare the taxon to compare
     * @return true if the label pair contains the taxon, false otherwise
     */
    protected boolean containsTaxon (String taxonToCompare) {
        return (this.first.equals(taxonToCompare) || this.second.equals(taxonToCompare));
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof LabelPair) {
            LabelPair other = (LabelPair) obj;
            return ((Objects.equals(this.first, other.first) && Objects.equals(this.second, other.second)) ||
                    Objects.equals(this.first, other.second) && Objects.equals(this.second, other.first));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result1 = 0;
        int result2 = 0;
        int prim = 17;

        if (first != null) {
            result1 = 31 * prim + first.hashCode();
        }
        if (second != null) {
            result2 = 31 * prim + second.hashCode();
        }
        return result1 + result2;
    }

    /**
     * Gets the first label.
     *
     * @return the first label
     */
    //neu eingefügt von Teammate
    public String getTaxon1() {
        return first;
    }

    public void setTaxon1(String taxon2) {
        this.first = taxon2;
    }


    /**
     * Gets the second label.
     *
     * @return the second label
     */
    public String getTaxon2() {
        return second;
    }

    public void setTaxon2(String taxon1) {
        this.second = taxon1;
    }
}
