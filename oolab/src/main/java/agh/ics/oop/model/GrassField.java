package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrassField extends AbstractWorldMap{

    private final int grassCount;
    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    public GrassField(int grassCount) {
        this.grassCount = grassCount;
        generateGrass();
    }

    @Override
    public boolean place(Animal animal) {
        updateCoordinates(animal);
        return super.place(animal);
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        super.move(animal, direction);
        updateCoordinates(animal);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if(objectAt(position) instanceof Grass){
            return true;
        }
        else return super.canMoveTo(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if(animals.containsKey(position)){
            return super.objectAt(position);
        }
        if(grasses.containsKey(position)){
            return grasses.get(position);
        }
        return null;
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> elements = super.getElements();
        elements.addAll(grasses.values());
        return elements;
    }

    private void updateCoordinates(Animal animal) {
        if(animal.getPosition().getX() > rightWidth){
            rightWidth = animal.getPosition().getX();
        }
        if(animal.getPosition().getX() < leftWidth){
            leftWidth = animal.getPosition().getX();
        }
        if(animal.getPosition().getY() > rightHeight){
            rightHeight = animal.getPosition().getY();
        }
        if(animal.getPosition().getY() < leftHeight){
            leftHeight = animal.getPosition().getY();
        }
    }

    public void generateGrass(){
        for(int i = 0; i < grassCount; i++){
            int r1 = (int) (Math.random() * Math.sqrt(grassCount * 10) + 1);
            int r2 = (int) (Math.random() * Math.sqrt(grassCount * 10) + 1);
            Vector2d position = new Vector2d(r1, r2);
            if (isOccupied(position)) {
                i--;
            }
            else{
                updateCoordinates(new Animal(position));
                Grass newGrass = new Grass(position);
                grasses.put(newGrass.getPosition(), newGrass);
            }
        }
    }


    public Map<Vector2d, Grass> getGrasses() {
        return grasses;
    }
}