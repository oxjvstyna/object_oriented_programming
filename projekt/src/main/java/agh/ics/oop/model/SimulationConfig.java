package agh.ics.oop.model;

public record SimulationConfig(AbstractWorldMap currentMap, MapVariant mapVariant, GrowthVariant growthVariant, int animalCount,
                               int simulationSpeed, AnimalConfig animalConfig) {
}
