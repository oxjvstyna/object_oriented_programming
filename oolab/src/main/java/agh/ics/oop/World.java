package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class World {
    public World() {
    }
    public static void main(String[] args) {
        System.out.println("system wystartowal");
        try{
            run(args);
            System.out.println("system zakonczyl dzialanie");
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2d> positions = List.of(new Vector2d(2,3), new Vector2d(1, 4));

            GrassField grassField = new GrassField(10);
            Simulation grassSimulation = new Simulation(positions, directions, grassField);
            grassSimulation.run();
        } catch (IllegalArgumentException e) {
            System.out.println("Wystapil blad: " + e.getMessage());
        }
    }
    public static void run(String[] args) {
        for(int i = 0; i < args.length; i++) {
            System.out.print(args[i]);
            if (i != args.length - 1) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }
        System.out.println("Start");
        List<MoveDirection> directions = OptionsParser.parse(args);
        for (MoveDirection direction : directions) {
            String message = switch (direction) {
                case FORWARD -> "Zwierzak idzie do przodu";
                case BACKWARD -> "Zwierzak idzie do tylu";
                case RIGHT -> "Zwierzak skreca w prawo";
                case LEFT -> "Zwierzak skreca w lewo";
            };
            System.out.println(message);
        }
        System.out.println("Stop");
    }
}
