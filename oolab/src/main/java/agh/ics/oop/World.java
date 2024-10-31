package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

public class World {
    public World() {
    }
    public static void main(String[] args) {
        System.out.println("system wystartowal");
        run(args);
        System.out.println("system zakonczyl dzialanie");
        Animal zwierzak = new Animal();
        System.out.println(zwierzak);
        zwierzak.move(MoveDirection.FORWARD);
        System.out.println(zwierzak);
        zwierzak.move(MoveDirection.FORWARD);
        zwierzak.move(MoveDirection.FORWARD);
        zwierzak.move(MoveDirection.FORWARD);
        zwierzak.move(MoveDirection.FORWARD);
        zwierzak.move(MoveDirection.RIGHT);
        zwierzak.move(MoveDirection.FORWARD);
        zwierzak.move(MoveDirection.FORWARD);
        zwierzak.move(MoveDirection.FORWARD);
        zwierzak.move(MoveDirection.FORWARD);
        zwierzak.move(MoveDirection.RIGHT);
        zwierzak.move(MoveDirection.FORWARD);
        zwierzak.move(MoveDirection.FORWARD);
        zwierzak.move(MoveDirection.FORWARD);
        zwierzak.move(MoveDirection.FORWARD);
        zwierzak.move(MoveDirection.FORWARD);
        zwierzak.move(MoveDirection.FORWARD);
        zwierzak.move(MoveDirection.FORWARD);
        zwierzak.move(MoveDirection.FORWARD);
        System.out.println(zwierzak);

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
        MoveDirection[] directions = OptionsParser.parse(args);
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
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
    }

}
