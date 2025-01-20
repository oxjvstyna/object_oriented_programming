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
        AnimalConfig animalConfig = new AnimalConfig(10, 10, 10, 10, 10, 10, predestination);
        GlobeMap map = new GlobeMap(50, 50, growthVariant, animalConfig);
        OwlbearMap map2 = new OwlbearMap(50, 50, growthVariant, animalConfig);
        ConsoleMapDisplay logger = new ConsoleMapDisplay();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        map.addObserver(logger);
        map2.addObserver(logger);
        map.addObserver((worldMap, message) -> System.out.println(LocalDateTime.now().format(formatter)));
        map2.addObserver((worldMap, message) -> System.out.println(LocalDateTime.now().format(formatter)));

        SimulationConfig config = new SimulationConfig(map, map, 10, growthVariant, 10000, 100, animalConfig);
        SimulationEngine engine = new SimulationEngine(new Simulation(config));
        engine.runAsyncInThreadPool();
    }
}
