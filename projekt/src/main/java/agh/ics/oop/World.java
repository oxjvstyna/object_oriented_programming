package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.beans.Observable;

import java.util.List;

public class World {
    public static void main(String[] args) {
        GrowthVariant growthVariant = new FertileEquator(10, 10);
        MoveVariant moveVariant = new TotalPredestination();
        EarthMap map = new EarthMap(10, 10, growthVariant, moveVariant);
        ConsoleMapDisplay logger = new ConsoleMapDisplay();
        int animalCount = 1;
        int simulationSteps = 5;
        map.addObserver(logger);

        SimulationConfig config = new SimulationConfig(map, growthVariant, animalCount, simulationSteps, moveVariant);

        Simulation simulation = new Simulation(config);
        simulation.run();
    }
}
