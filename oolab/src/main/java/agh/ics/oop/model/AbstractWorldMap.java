package agh.ics.oop.model;

import agh.ics.oop.World;
import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap<Animal, Vector2d> {

    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final UUID id = UUID.randomUUID();
    protected Vector2d lowerLeft = new Vector2d(0, 0);
    protected Vector2d upperRight = new Vector2d(0, 0);
    protected final List<MapChangeListener> observers = new ArrayList<>();

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

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d initialPosition = animal.getPosition();
        animals.remove(animal.getPosition(), (Animal) animal);
        animal.move(direction, this);
        animals.put(animal.getPosition(), animal);
        notifyObservers("Zwierze ruszylo z " + initialPosition + " do " + animal.getPosition());
    }

    @Override
    public String toString() {
        MapVisualizer toDraw = new MapVisualizer(this);
        return toDraw.draw(getCurrentBounds().lowerLeft(), getCurrentBounds().upperRight());
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
                (position.follows(lowerLeft)) &&
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

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(lowerLeft, upperRight);
    }

    @Override
    public UUID getID(){
        return id;
    }
}
