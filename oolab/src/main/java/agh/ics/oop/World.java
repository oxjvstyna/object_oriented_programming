package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {

        System.out.println("System wystartowal");
        try{

            List<Simulation> simulations = new ArrayList<>();
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2d> positions = List.of(new Vector2d(2,3), new Vector2d(9, 4));
            ConsoleMapDisplay logger = new ConsoleMapDisplay();

            for (int i = 0; i < 1000; i++){
                GrassField grassField = new GrassField(10);
                RectangularMap rectangle = new RectangularMap(2, 10);
                grassField.addObserver(logger);
                rectangle.addObserver(logger);
                simulations.add(new Simulation(positions, directions, grassField));
                simulations.add(new Simulation(positions, directions, rectangle));
            }

            SimulationEngine engine = new SimulationEngine(simulations);
            engine.runAsyncInThreadPool();
        } catch (IllegalArgumentException e) {
            System.out.println("Wystapil blad: " + e.getMessage());
        }
        System.out.println("System zakonczyl dzialanie");
    }
}
