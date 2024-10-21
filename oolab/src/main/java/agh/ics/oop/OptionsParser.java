package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.Arrays;

public class OptionsParser {
    public OptionsParser() {
    }
    public static MoveDirection[] parse(String[] args) {
        MoveDirection[] directions = new MoveDirection[args.length];
        int index = 0;

        for (String arg : args) {
            MoveDirection value = switch (arg) {
                case "f" -> MoveDirection.FORWARD;
                case "b" -> MoveDirection.BACKWARD;
                case "r" -> MoveDirection.RIGHT;
                case "l" -> MoveDirection.LEFT;
                default -> null;
                };
            if (value != null){
                directions[index] = value;
                index++;
            }
        }
        return Arrays.copyOfRange(directions, 0, index);
    }
}
