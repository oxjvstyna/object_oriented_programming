package agh.ics.oop;

import agh.ics.oop.model.*;

public class Simulation implements Runnable {
    protected SimulationConfig simConfig;

    public Simulation(SimulationConfig config) {
        this.simConfig = config;
        }

    @Override
    public void run() {
        simConfig.currentMap().initializeMap(simConfig.animalCount());
        int steps = simConfig.simulationSteps();
        for (int i = 0; i < steps; i++) {
            simConfig.currentMap().handleMap();
        }
        simConfig.currentMap().getReport();
    }

    public SimulationConfig getSimConfig() {
        return simConfig;
    }

    public void runStep() {
        simConfig.currentMap().handleMap();
    }
}
