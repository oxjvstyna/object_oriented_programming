package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;

public class GrassField implements WorldMap<WorldElement, Vector2d> {

    private final int grassCount;
    private final Map<Vector2d, Animal> animals = new HashMap<>();
    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    private int rightWidth = 0;
    private int rightHeight = 0;
    private int leftWidth = 0;
    private int leftHeight = 0;

    public GrassField(int grassCount) {
        this.grassCount = grassCount;
    }

    @Override
    public String toString() {
        MapVisualizer toDraw = new MapVisualizer(this);
        return toDraw.draw(new Vector2d(leftWidth, leftHeight), new Vector2d(rightWidth, rightHeight));
    }

    @Override
    public boolean place(WorldElement object) {
        updateCoordinates(object);
        if(canMoveTo(object.getPosition())){
            if (object instanceof Animal) {
                animals.put(object.getPosition(), (Animal) object);
            }
            else {
                grasses.put(object.getPosition(), (Grass) object);
            }
            return true;
        }
        return false;
    }

    private void updateCoordinates(WorldElement object) {
        if(object.getPosition().getX() > rightWidth){
            rightWidth = object.getPosition().getX();
        }
        if(object.getPosition().getX() < leftWidth){
            leftWidth = object.getPosition().getX();
        }
        if(object.getPosition().getY() > rightHeight){
            rightHeight = object.getPosition().getY();
        }
        if(object.getPosition().getY() < leftHeight){
            leftHeight = object.getPosition().getY();
        }
    }

    @Override
    public void move(WorldElement animal, MoveDirection direction) {
        animals.remove(animal.getPosition(), (Animal) animal);
        ((Animal) animal).move(direction, this);
        updateCoordinates(animal);
        animals.put(animal.getPosition(), (Animal) animal);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position) || grasses.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if(animals.containsKey(position)){
            return animals.get(position);
        }
        if(grasses.containsKey(position)){
            return grasses.get(position);
        }
        return null;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (grasses.containsKey(position)){
            return true;
        }
        else return !isOccupied(position);
    }

    public void generateGrass(){
        for(int i = 0; i < grassCount; i++){
            int r1 = (int) (Math.random() * Math.sqrt(grassCount * 10) + 1);
            int r2 = (int) (Math.random() * Math.sqrt(grassCount * 10) + 1);
            Vector2d position = new Vector2d(r1, r2);
            if (isOccupied(position)){
                i--;
            }
            Grass newGrass = new Grass(position);
            this.place(newGrass);
        }
    }
}