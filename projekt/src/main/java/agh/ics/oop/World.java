package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.beans.Observable;

import java.util.List;

public class World {
    public static void main(String[] args) {
        GrowthVariant equator = new FertileEquator(2, 10);
        EarthMap rectangle = new EarthMap(2, 10, equator);
        ConsoleMapDisplay logger = new ConsoleMapDisplay();

        Genome genome = new Genome(8);
        String[] genomeAsStrings = genome.getGenesAsStrings();

        List<MoveDirection> directions = OptionsParser.parse(genomeAsStrings);
        List<Vector2d> positions = List.of(new Vector2d(2, 3));
        rectangle.addObserver(logger);

        Simulation simulation = new Simulation(positions, directions, rectangle);
        simulation.run();
    }
}
