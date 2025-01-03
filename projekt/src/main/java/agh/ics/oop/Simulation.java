package agh.ics.oop;

import agh.ics.oop.model.*;

public class Simulation implements Runnable {
    protected SimulationConfig simConfig;

    public Simulation(SimulationConfig config) {
        this.simConfig = config;
        }


    public void run() {
        simConfig.getCurrentMap().initializeMap(simConfig.getAnimalCount());
        int steps = simConfig.getSimulationSteps();
        for (int i = 0; i < steps; i++) {
            try {
                simConfig.getCurrentMap().handleMap();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        simConfig.getCurrentMap().getReport();
    }


}
