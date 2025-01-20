package agh.ics.oop.model;

public enum  MapDirection {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    NORTH_EAST,
    SOUTH_EAST,
    NORTH_WEST,
    SOUTH_WEST;

    public String toString() {
        return switch(this) {
            case NORTH -> "Polnoc";
            case SOUTH -> "Poludnie";
            case EAST -> "Wschod";
            case WEST -> "Zachod";
            case NORTH_EAST -> "Polnocny Wschod";
            case SOUTH_EAST -> "Poludniowy Wschod";
            case NORTH_WEST -> "Polnocny Zachod";
            case SOUTH_WEST -> "Poludniowy Zachod";
        };
    }
    public MapDirection next(int ordinal) {
        return switch(this) {
            case NORTH -> NORTH_EAST;
            case NORTH_EAST -> EAST;
            case EAST -> SOUTH_EAST;
            case SOUTH_EAST -> SOUTH;
            case SOUTH -> SOUTH_WEST;
            case SOUTH_WEST -> WEST;
            case WEST -> NORTH_WEST;
            case NORTH_WEST -> NORTH;
        };
    }
    public MapDirection previous(int i) {
        return switch(this) {
            case NORTH -> NORTH_WEST;
            case NORTH_WEST -> WEST;
            case WEST -> SOUTH_WEST;
            case SOUTH_WEST -> SOUTH;
            case SOUTH -> SOUTH_EAST;
            case SOUTH_EAST -> EAST;
            case EAST -> NORTH_EAST;
            case NORTH_EAST -> NORTH;
        };
    }
    public Vector2d toUnitVector() {
        return switch(this){
            case NORTH -> new Vector2d(0,1);
            case NORTH_EAST -> new Vector2d(1,1);
            case SOUTH -> new Vector2d(0,-1);
            case SOUTH_EAST -> new Vector2d(1,-1);
            case EAST -> new Vector2d(1,0);
            case NORTH_WEST -> new Vector2d(-1,1);
            case WEST -> new Vector2d(-1,0);
            case SOUTH_WEST -> new Vector2d(-1,-1);
        };
    }
}

