package agh.ics.oop;

import agh.ics.oop.model.ConsoleMapDisplay;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.EarthMap;
import agh.ics.oop.model.Vector2d;

import java.util.List;

public class World {
    public static void main(String[] args) {
        EarthMap rectangle = new EarthMap(2, 10);
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<Vector2d> positions = List.of(new Vector2d(2,3), new Vector2d(9, 4));
        ConsoleMapDisplay logger = new ConsoleMapDisplay();
        Simulation simulation = new Simulation(positions, directions, rectangle);
        rectangle.addObserver(logger);
        simulation.run();
        }
    }