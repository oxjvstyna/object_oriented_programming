package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.FileMapDisplay;
import javafx.application.Application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


            for (int i = 0; i < 1; i++){
                GrassField grassField = new GrassField(10);
                FileMapDisplay fileObserver = new FileMapDisplay(grassField.getID());
                RectangularMap rectangle = new RectangularMap(2, 10);
                FileMapDisplay secondFileObserver = new FileMapDisplay(rectangle.getID());
                grassField.addObserver(fileObserver);
                rectangle.addObserver(secondFileObserver);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                rectangle.addObserver((worldMap, message) ->{
                    String currentTime = LocalDateTime.now().format(formatter);
                    System.out.println(currentTime + " " + message);
                });
                grassField.addObserver((worldMap, message) ->{
                    String currentTime = LocalDateTime.now().format(formatter);
                    System.out.println(currentTime + " " + message);
                });


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
