package agh.ics.oop.model;

import agh.ics.oop.model.util.IncorrectPositionException;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap<Animal, Vector2d> {

    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected int width;
    protected int height;
    protected final List<MapChangeListener> observers = new ArrayList<>();

    public AbstractWorldMap(int width, int height) {
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width, height);
        this.width = width;
        this.height = height;
    }

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

    protected Vector2d adjustPosition(Vector2d position, Animal animal) {
        return position;
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d initialPosition = animal.getPosition();
        animals.remove(animal.getPosition(), (Animal) animal);
        animal.move(direction, this);
        Vector2d newPosition = adjustPosition(animal.getPosition(), animal);
        animals.put(newPosition, animal);
        notifyObservers("Zwierze ruszylo z " + initialPosition + " do " + animal.getPosition());
    }

    @Override
    public void place(Animal animal) throws IncorrectPositionException {
        if(canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(), animal);
            notifyObservers("Zwierze umieszczone na pozycji " + animal.getPosition());
        }
        else{
            throw new IncorrectPositionException(animal.getPosition());
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.precedes(upperRight)) &&
                (position.follows(lowerLeft));
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
