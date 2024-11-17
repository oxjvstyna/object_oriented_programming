package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap<Animal, Vector2d> {

    protected final Map<Vector2d, Animal> animals = new HashMap<>();

    protected int rightWidth = 0;
    protected int rightHeight = 0;
    protected int leftWidth = 0;
    protected int leftHeight = 0;

    @Override
    public void move(Animal animal, MoveDirection direction) {
        animals.remove(animal.getPosition(), (Animal) animal);
        animal.move(direction, this);
        animals.put(animal.getPosition(), animal);
    }

    @Override
    public String toString() {
        MapVisualizer toDraw = new MapVisualizer(this);
        return toDraw.draw(new Vector2d(leftWidth, leftHeight), new Vector2d(rightWidth, rightHeight));
    }

    @Override
    public boolean place(Animal animal) {
        if(canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(), animal);
            return true;
        }
        return false;
    }


    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.precedes(new Vector2d(rightWidth, rightHeight))) &&
                (position.follows(new Vector2d(leftWidth, leftHeight))) &&
                !isOccupied(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public List<WorldElement> getElements() {
        return new ArrayList<>(animals.values());
    }

}
