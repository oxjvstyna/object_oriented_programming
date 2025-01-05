package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.beans.Observable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class World {
    public static void main(String[] args) {
        GrowthVariant growthVariant = new FertileEquator(50, 50);
        MoveVariant predestination = new TotalPredestination();
        MoveVariant crazy = new SlightMadness();
        EarthMap map = new EarthMap(50, 50, growthVariant, predestination);
        ConsoleMapDisplay logger = new ConsoleMapDisplay();
        map.addObserver(logger);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        map.addObserver((worldMap, message) ->{
                String currentTime = LocalDateTime.now().format(formatter);
                System.out.println(currentTime);
        });
        int animalCount = 5000;
        int simulationSteps = 100;

        SimulationConfig config = new SimulationConfig(map, growthVariant, animalCount, simulationSteps, predestination);

        Simulation simulation = new Simulation(config);

        SimulationEngine engine = new SimulationEngine(simulation);
        engine.runAsyncInThreadPool();
    }
}
