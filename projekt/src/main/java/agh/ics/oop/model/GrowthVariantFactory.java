package agh.ics.oop.model;

public class GrowthVariantFactory {

    public static GrowthVariant create(String growthVariant, int width, int height) {
        if (growthVariant.equals("FertileEquator")) {
            return new FertileEquator(width, height);
        }
        throw new IllegalArgumentException("Unknown growth variant: " + growthVariant);
    }
}
