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
                case "0" -> MoveDirection.FORWARD;
                case "1" -> MoveDirection.FORWARD_RIGHT;
                case "2" -> MoveDirection.RIGHT;
                case "3" -> MoveDirection.BACKWARD_RIGHT;
                case "4" -> MoveDirection.BACKWARD;
                case "5" -> MoveDirection.BACKWARD_LEFT;
                case "6" -> MoveDirection.LEFT;
                case "7" -> MoveDirection.FORWARD_LEFT;
                default -> throw new IllegalArgumentException(arg + " jest nieprawidlowym ruchem.");
            })
                .collect(Collectors.toList());
        }
    }
