package agh.ics.oop.model;

public class MapFactory {

    public static AbstractWorldMap create(MapVariant mapVariant, int width, int height, GrowthVariant growthVariant, AnimalConfig animalConfig) {

        return switch (mapVariant) {
            case GlobeMap ignored -> new GlobeMap(width, height, growthVariant, animalConfig);
            case OwlbearMap ignored -> new OwlbearMap(width, height, growthVariant, animalConfig);
            default -> throw new IllegalArgumentException("Unknown map variant: " + mapVariant);
        };
    }
}
