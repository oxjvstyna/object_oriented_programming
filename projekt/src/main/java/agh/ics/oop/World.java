package agh.ics.oop;

import agh.ics.oop.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class World {
    public static void main(String[] args) {
        GrowthVariant growthVariant = new FertileEquator(50, 50);
        MoveVariant predestination = new TotalPredestination();
        GlobeMap map = new GlobeMap(50, 50, growthVariant, predestination);
        OwlbearMap map2 = new OwlbearMap(50, 50, growthVariant, predestination);
        ConsoleMapDisplay logger = new ConsoleMapDisplay();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        map.addObserver(logger);
        map2.addObserver(logger);
        map.addObserver((worldMap, message) -> System.out.println(LocalDateTime.now().format(formatter)));
        map2.addObserver((worldMap, message) -> System.out.println(LocalDateTime.now().format(formatter)));

        SimulationConfig config = new SimulationConfig(map, growthVariant, 5000, 100, predestination);
        SimulationEngine engine = new SimulationEngine(new Simulation(config));
        engine.runAsyncInThreadPool();
    }
}
