package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.RectangularMap;
import agh.ics.oop.model.Vector2d;

import java.util.List;

public class World {
    public World() {
    }
    public static void main(String[] args) {
        System.out.println("system wystartowal");
        run(args);
        System.out.println("system zakonczyl dzialanie");

        RectangularMap map = new RectangularMap(10, 10);
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3, 4), new Vector2d(5, 6));
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();

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
