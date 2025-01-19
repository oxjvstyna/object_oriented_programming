package agh.ics.oop.model;

public class MapFactory {

    /**
     * Tworzy mapę na podstawie wybranego wariantu.
     *
     * @param mapVariant   Wariant mapy ("GlobeMap" lub "OwlbearMap").
     * @param width        Szerokość mapy.
     * @param height       Wysokość mapy.
     * @param growthVariant Wariant wzrostu roślin.
     * @param animalConfig Konfiguracja zwierząt.
     * @return Utworzona mapa.
     * @throws IllegalArgumentException Jeśli wariant mapy jest nieznany.
     */
    public static AbstractWorldMap create(String mapVariant, int width, int height, String growthVariant, AnimalConfig animalConfig) {
        GrowthVariant growthStrategy = GrowthVariantFactory.create(growthVariant, width, height);

        return switch (mapVariant) {
            case "GlobeMap" -> new GlobeMap(width, height, growthStrategy, animalConfig);
            case "OwlbearMap" -> new OwlbearMap(width, height, growthStrategy, animalConfig);
            default -> throw new IllegalArgumentException("Unknown map variant: " + mapVariant);
        };
    }
}
