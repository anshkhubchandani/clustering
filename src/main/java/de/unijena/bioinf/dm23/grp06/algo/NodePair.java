package de.unijena.bioinf.dm23.grp06.algo;

import java.util.Objects;

public class NodePair {

    String first;
    String second;

    public NodePair(String firstTaxon, String secondTaxon) {
        this.first = firstTaxon;
        this.second = secondTaxon;
    }

    protected boolean containsTaxon (String taxonToCompare) {
        return (this.first.equals(taxonToCompare) || this.second.equals(taxonToCompare));
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof NodePair) {
            NodePair other = (NodePair) obj;
            return ((Objects.equals(this.first, other.first) && Objects.equals(this.second, other.second)) ||
                    Objects.equals(this.first, other.second) && Objects.equals(this.second, other.first));
        }
        return false;
    }

    @Override
    public int hashCode() {
        // TODO !!!
        return Objects.hash(first, second);
    }

    //neu eingefügt von Teammate
    public String getTaxon2() {
        return first;
    }

    public void setTaxon2(String taxon2) {
        this.first = taxon2;
    }

    public String getTaxon1() {
        return second;
    }

    public void setTaxon1(String taxon1) {
        this.second = taxon1;
    }
}
