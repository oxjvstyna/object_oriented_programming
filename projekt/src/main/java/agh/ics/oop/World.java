package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.beans.Observable;

import java.util.List;

public class World {
    public static void main(String[] args) {
        GrowthVariant growthVariant = new FertileEquator(20, 20);
        MoveVariant predestination = new TotalPredestination();
        MoveVariant crazy = new SlightMadness();
        EarthMap map = new EarthMap(20, 20, growthVariant, predestination);
        ConsoleMapDisplay logger = new ConsoleMapDisplay();
        int animalCount = 20;
        int simulationSteps = 1000;
        map.addObserver(logger);

        SimulationConfig config = new SimulationConfig(map, growthVariant, animalCount, simulationSteps, predestination);

        Simulation simulation = new Simulation(config);
        simulation.run();
    }
}
