package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Map;

public class RectangularMap implements WorldMap {
    private static Map<Vector2d, Animal> animals = new HashMap<>();
    private final int width;
    private final int height;


    public RectangularMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public String toString(RectangularMap map) {
        MapVisualizer toDraw = new MapVisualizer(map);
        toDraw.draw(new Vector2d(0, 0), new Vector2d(width, height));
        return toDraw.toString();
    }


    @Override
    public boolean place(Animal animal) {
        if (!isOccupied(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            return true;
        }
        return false;
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        animal.move(direction);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal animal : animals.values()) {
            if (animal.isAt(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Animal objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.precedes(new Vector2d(width, height)) &&
                (position.follows(new Vector2d(0, 0))) &&
                !isOccupied(position);
    }
}

