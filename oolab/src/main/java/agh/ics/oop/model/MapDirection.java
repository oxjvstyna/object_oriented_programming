package agh.ics.oop.model;

import java.util.Map;
import java.util.SortedMap;

public enum  MapDirection {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public String toString() {
        return switch(this) {
            case NORTH -> "Polnoc";
            case SOUTH -> "Poludnie";
            case EAST -> "Wschod";
            case WEST -> "Zachod";
        };
    }
    public MapDirection next() {
        return switch(this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }
    public MapDirection previous() {
        return switch(this) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
        };
    }
    public Vector2d toUnitVector() {
        return switch(this){
            case NORTH -> new Vector2d(0,1);
            case SOUTH -> new Vector2d(0,-1);
            case EAST -> new Vector2d(1,0);
            case WEST -> new Vector2d(-1,0);
        };
    }
}

