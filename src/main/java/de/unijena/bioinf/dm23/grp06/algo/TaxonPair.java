package de.unijena.bioinf.dm23.grp06.algo;

import java.util.Objects;

public class TaxonPair {

    String first;
    String second;

    public TaxonPair (String firstTaxon, String secondTaxon) {
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
        if (obj instanceof TaxonPair) {
            TaxonPair other = (TaxonPair) obj;
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
}
