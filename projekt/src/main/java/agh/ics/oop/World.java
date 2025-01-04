package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.beans.Observable;

import java.util.List;

public class World {
    public static void main(String[] args) {
        GrowthVariant growthVariant = new FertileEquator(10, 10);
        MoveVariant predestination = new TotalPredestination();
        MoveVariant crazy = new SlightMadness();
        EarthMap map = new EarthMap(10, 10, growthVariant, predestination);
        ConsoleMapDisplay logger = new ConsoleMapDisplay();
        int animalCount = 50;
        int simulationSteps = 20;
        map.addObserver(logger);

        SimulationConfig config = new SimulationConfig(map, growthVariant, animalCount, simulationSteps, predestination);

        Simulation simulation = new Simulation(config);
        simulation.run();
    }
}
