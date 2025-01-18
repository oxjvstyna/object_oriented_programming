package agh.ics.oop.model;

public record SimulationConfig(AbstractWorldMap currentMap, GrowthVariant growthVariant, int animalCount,
                               int simulationSteps, MoveVariant moveVariant) {
}
