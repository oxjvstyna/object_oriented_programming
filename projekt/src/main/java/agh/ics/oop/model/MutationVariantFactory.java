package agh.ics.oop.model;

public class MutationVariantFactory {

    /**
     * Tworzy wariant mutacji na podstawie wybranego wariantu.
     *
     * @param mutationVariant Wariant mutacji (np. "TotalRandomness", "SlightAdjustment").
     * @return Strategia mutacji.
     * @throws IllegalArgumentException Jeśli wariant mutacji jest nieznany.
     */
    public static MutationVariant create(String mutationVariant) {
        if (mutationVariant.equals("FullRandomness")) {
            return new FullRandomness();
        }
        throw new IllegalArgumentException("Unknown mutation variant: " + mutationVariant);
    }
}
