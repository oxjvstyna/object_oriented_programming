package agh.ics.oop.model;

public class Animal {
    private static final Vector2d UPPER_LIMIT = new Vector2d(4, 4);
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
        return position.toString() + " " + orientation.toString();
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveDirection direction){
        switch (direction){
            case RIGHT: this.orientation = this.orientation.next();
            break;
            case LEFT: this.orientation = this.orientation.previous();
            break;
            case FORWARD, BACKWARD: // tutaj dodaj dolny i gorny limit lepiej
                if (this.position.add(this.orientation.toUnitVector()).precedes(UPPER_LIMIT)){
                    this.position = this.position.add(this.orientation.toUnitVector());
                }
            break;
        }
    }
}
