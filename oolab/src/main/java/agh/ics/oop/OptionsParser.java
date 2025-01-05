package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionsParser {
    public OptionsParser() {
    }
    public static List<MoveDirection> parse(String[] args) {
        return Stream.of(args)
                .map(arg -> switch(arg){
                    case "f" -> MoveDirection.FORWARD;
                    case "r" -> MoveDirection.RIGHT;
                    case "b" -> MoveDirection.BACKWARD;
                    case "l" -> MoveDirection.LEFT;
                    default -> throw new IllegalArgumentException(arg + " jest nieprawidlowym ruchem.");
                })
                .collect(Collectors.toList());
    }
}
