package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    protected SimulationConfig simConfig;

    public Simulation(SimulationConfig config) {
        this.simConfig = config;
        }


    public void run() {
        simConfig.getCurrentMap().initializeMap(simConfig.getAnimalCount());
        int steps = simConfig.getSimulationSteps();
//        for (int i = 0; i < steps; i++) {
//            simConfig.getCurrentMap().handleMap();
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                break;
//            }
//        }
    }


}
