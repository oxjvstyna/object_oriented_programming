package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public OptionsParser() {
    }
    public static List<MoveDirection> parse(String[] args) {
        List<MoveDirection> directions = new ArrayList<MoveDirection>();
        for (String arg : args) {
            switch (arg) {
                case "f" -> directions.add(MoveDirection.FORWARD);
                case "b" -> directions.add(MoveDirection.BACKWARD);
                case "r" -> directions.add(MoveDirection.RIGHT);
                case "l" -> directions.add(MoveDirection.LEFT);
                default -> throw new IllegalArgumentException(arg + " jest nieprawidlowym ruchem.");
            }
        }
        return directions;
    }
}
