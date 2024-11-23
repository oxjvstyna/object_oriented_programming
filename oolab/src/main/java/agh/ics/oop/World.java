package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class World {
    public static void main(String[] args) {

        System.out.println("System wystartowal");
        try{
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2d> positions = List.of(new Vector2d(2,3), new Vector2d(9, 4));

            GrassField grassField = new GrassField(10);
            ConsoleMapDisplay logger = new ConsoleMapDisplay();
            grassField.addObserver(logger);

            Simulation grassSimulation = new Simulation(positions, directions, grassField);
            grassSimulation.run();
        } catch (IllegalArgumentException e) {
            System.out.println("Wystapil blad: " + e.getMessage());
        }
        System.out.println("System zakonczyl dzialanie");
    }
}
