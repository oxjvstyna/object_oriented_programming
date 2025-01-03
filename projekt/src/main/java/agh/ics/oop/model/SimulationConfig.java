package agh.ics.oop.model;

import java.util.List;

public class SimulationConfig {
    private final int animalCount;
    private final MoveVariant moveVariant;
    private final AbstractWorldMap currentMap;
    private final GrowthVariant growthVariant;
    private final int simulationSteps;


    public SimulationConfig(
            AbstractWorldMap currentMap,
            GrowthVariant growthVariant,
            int animalCount,
            int simulationSteps,
            MoveVariant moveVariant) {
        this.moveVariant = moveVariant;
        this.currentMap = currentMap;
        this.growthVariant = growthVariant;
        this.simulationSteps = simulationSteps;
        this.animalCount = animalCount;
    }
    public AbstractWorldMap getCurrentMap() {
        return currentMap;
    }
    public GrowthVariant getGrowthVariant() {
        return growthVariant;
    }
    public MoveVariant getMoveVariant() {
        return moveVariant;
    }

    public int getSimulationSteps() {
        return simulationSteps;
    }

    public int getAnimalCount() {
        return animalCount;
    }
}
