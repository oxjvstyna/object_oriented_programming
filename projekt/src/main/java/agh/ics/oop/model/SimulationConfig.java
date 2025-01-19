package agh.ics.oop.model;

public record SimulationConfig(AbstractWorldMap currentMap, GrowthVariant growthVariant, int animalCount,
                               int simulationSpeed, MoveVariant moveVariant) {
}
