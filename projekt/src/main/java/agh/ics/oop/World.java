package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.beans.Observable;

import java.util.List;

public class World {
    public static void main(String[] args) {
        GrowthVariant growthVariant = new FertileEquator(100, 100);
        MoveVariant predestination = new TotalPredestination();
        MoveVariant crazy = new SlightMadness();
        EarthMap map = new EarthMap(100, 100, growthVariant, predestination);
        ConsoleMapDisplay logger = new ConsoleMapDisplay();
        int animalCount = 50;
        int simulationSteps = 1000;
        map.addObserver(logger);

        SimulationConfig config = new SimulationConfig(map, growthVariant, animalCount, simulationSteps, predestination);

        Simulation simulation = new Simulation(config);
        simulation.run();
    }
}
