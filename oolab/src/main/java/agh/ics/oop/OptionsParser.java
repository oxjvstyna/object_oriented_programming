package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.Arrays;

public class OptionsParser {
    public OptionsParser() {
    }
    public static MoveDirection[] parse(String[] args) {
        MoveDirection[] directions = new MoveDirection[args.length];
        int cnt = 0;
        int j = 0;
        for (String arg : args) {
            MoveDirection value;
            switch (arg) {
                case "f" -> value = MoveDirection.FORWARD;
                case "b" -> value = MoveDirection.BACKWARD;
                case "r" -> value = MoveDirection.RIGHT;
                case "l" -> value = MoveDirection.LEFT;
                default -> {
                    cnt++;
                    value = MoveDirection.NULL;
                }
            }
            if (value != MoveDirection.NULL) {
                directions[j] = value;
            } else j--;
            j++;
        }
        return Arrays.copyOfRange(directions, 0, directions.length - cnt);
    }
}
