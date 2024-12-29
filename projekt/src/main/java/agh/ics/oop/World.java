package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class World {
    public static void main(String[] args) {
        GrowthVariant equator = new FertileEquator(2, 10);
        EarthMap rectangle = new EarthMap(2, 10, equator);
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<Vector2d> positions = List.of(new Vector2d(2,3), new Vector2d(9, 4));
        ConsoleMapDisplay logger = new ConsoleMapDisplay();
        Simulation simulation = new Simulation(positions, directions, rectangle);
        rectangle.addObserver(logger);
        simulation.run();
        }
    }