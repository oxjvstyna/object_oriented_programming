package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;

public class GrassField extends AbstractWorldMap{

    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    public GrassField(int grassCount) {
        int maxWidth = (int) (Math.sqrt(10 * grassCount) + 1);
        int maxHeight = (int) (Math.sqrt(10 * grassCount) + 1);

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(maxWidth, maxHeight, grassCount);
        for (Vector2d grassPosition : randomPositionGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
        }
        updateCoordinates(new Animal(new Vector2d(maxWidth, maxHeight)));
    }

    @Override
    public boolean place(Animal animal) {
        updateCoordinates(animal);
        return super.place(animal);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        updateCoordinates(new Animal(position));
        if(objectAt(position) instanceof Grass){
            return true;
        }
        else return !isOccupied(position);
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

    public Map<Vector2d, Grass> getGrasses() {
        return grasses;
    }
}