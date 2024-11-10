package agh.ics.oop.model;

public class Animal {
    private static final Vector2d UPPER_LIMIT = new Vector2d(4, 4);
    private static final Vector2d LOWER_LIMIT = new Vector2d(0, 0);
    private Vector2d position;
    private MapDirection orientation;

    public Animal() {
        this.orientation = MapDirection.NORTH;
        this.position = new Vector2d(2,2);
    }

    public Animal(Vector2d position) {
        this.orientation = MapDirection.NORTH;
        this.position = position;
    }

    @Override
    public String toString() {
        return switch (orientation){
            case EAST -> ">";
            case NORTH -> "^";
            case SOUTH -> "S";
            case WEST -> "<";
        };
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public MapDirection getOrientation() {
        return this.orientation;
    }

    public void move(MoveDirection direction, MoveValidator validator) {
        switch (direction){
            case RIGHT: this.orientation = this.orientation.next();
            break;
            case LEFT: this.orientation = this.orientation.previous();
            break;
            case FORWARD:
                Vector2d nextPositionForward = this.position.add(orientation.toUnitVector());
                if(validator.canMoveTo(nextPositionForward)) {
                    this.position = nextPositionForward;
                }
                break;
            case BACKWARD:
                Vector2d nextPositionBackward = this.position.subtract(orientation.toUnitVector());
                if(validator.canMoveTo(nextPositionBackward)) {
                    this.position = nextPositionBackward;
                }
                break;
        }
    }
}
