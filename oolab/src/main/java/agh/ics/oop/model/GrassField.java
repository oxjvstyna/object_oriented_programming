package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GrassField extends AbstractWorldMap{

    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    public GrassField(int grassCount) {
        int maxWidth = (int) (Math.sqrt(10 * grassCount) + 1);
        int maxHeight = (int) (Math.sqrt(10 * grassCount) + 1);

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(maxWidth, maxHeight, grassCount);
        for (Vector2d grassPosition : randomPositionGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
        }
    }

    @Override
    public void place(Animal animal) throws IncorrectPositionException {
        super.place(animal);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
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
        return Stream.concat(super.getElements().stream(), grasses.values().stream())
                .collect(Collectors.toList());
    }

    @Override
    public Boundary getCurrentBounds(){
        Vector2d left = new Vector2d(upperRight.getX(), upperRight.getY());
        Vector2d right = new Vector2d(lowerLeft.getX(), lowerLeft.getY());
        List<WorldElement> elements = getElements();
        for(WorldElement element : elements){
            left = left.lowerLeft(element.getPosition());
            right = right.upperRight(element.getPosition());
        }
        return new Boundary(left, right);
    }


    public Map<Vector2d, Grass> getGrasses() {
        return grasses;
    }
}