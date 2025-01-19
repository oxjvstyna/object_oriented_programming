package agh.ics.oop.model;

public class GrowthVariantFactory {

    /**
     * Tworzy strategię wzrostu roślin na podstawie wybranego wariantu.
     *
     * @param growthVariant Wariant wzrostu roślin.
     * @return Strategia wzrostu roślin.
     * @throws IllegalArgumentException Jeśli wariant wzrostu jest nieznany.
     */
    public static GrowthVariant create(String growthVariant, int width, int height) {
        if (growthVariant.equals("FertileEquator")) {
            return new FertileEquator(width, height);
        }
        throw new IllegalArgumentException("Unknown growth variant: " + growthVariant);
    }
}
