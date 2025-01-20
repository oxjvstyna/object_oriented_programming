package agh.ics.oop.model;

public class MapVariantFactory {

    public static MapVariant create(String mapVariant, int width, int height, GrowthVariant growthVariant, AnimalConfig animalConfig) {
        if (mapVariant.equals("GlobeMap")) {
            return new GlobeMap(width, height, growthVariant, animalConfig);
        }
        if (mapVariant.equals("OwlbearMap")) {
            return new OwlbearMap(width, height, growthVariant, animalConfig);
        }
        throw new IllegalArgumentException("Unknown map variant: " + mapVariant);
    }
}
