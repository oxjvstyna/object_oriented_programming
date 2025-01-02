package agh.ics.oop.model;

import java.util.List;

public class SimulationConfig {
    private int animalCount;
    private final AbstractWorldMap currentMap;
    private final GrowthVariant growthVariant;
    private final int simulationSteps;


    public SimulationConfig(
            AbstractWorldMap currentMap,
            GrowthVariant growthVariant,
            int animalCount,
            int simulationSteps) {
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

    public int getSimulationSteps() {
        return simulationSteps;
    }

    public int getAnimalCount() {
        return animalCount;
    }
}
