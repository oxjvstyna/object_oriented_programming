package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.beans.Observable;

import java.util.List;

public class World {
    public static void main(String[] args) {
        GrowthVariant growthVariant = new FertileEquator(2, 10);
        EarthMap map = new EarthMap(2, 10, growthVariant);
        ConsoleMapDisplay logger = new ConsoleMapDisplay();

        int animalCount = 4;
        int simulationSteps = 100;
        map.addObserver(logger);

        SimulationConfig config = new SimulationConfig(map, growthVariant, animalCount, simulationSteps);

        Simulation simulation = new Simulation(config);
        simulation.run();
    }
}
