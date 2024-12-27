package agh.ics.oop.model;

import java.util.Map;

public class RectangularMap extends AbstractWorldMap {

    public RectangularMap(int width, int height) {
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width, height);
    }
    public Map<Vector2d, Animal> getAnimals() {
        return animals;
    }
}

