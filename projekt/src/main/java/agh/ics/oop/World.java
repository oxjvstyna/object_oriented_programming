package agh.ics.oop;

import agh.ics.oop.model.ConsoleMapDisplay;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.RectangularMap;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        RectangularMap rectangle = new RectangularMap(2, 10);
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<Vector2d> positions = List.of(new Vector2d(2,3), new Vector2d(9, 4));
        ConsoleMapDisplay logger = new ConsoleMapDisplay();
        Simulation simulation = new Simulation(positions, directions, rectangle);
        rectangle.addObserver(logger);
        simulation.run();

        }
    }