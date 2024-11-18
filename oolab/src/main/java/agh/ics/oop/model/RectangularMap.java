package agh.ics.oop.model;

import java.util.Map;

public class RectangularMap extends AbstractWorldMap {

    public RectangularMap(int width, int height) {
        this.rightWidth = width;
        this.rightHeight = height;
        this.leftWidth = 0;
        this.leftHeight = 0;
    }

    public Map<Vector2d, Animal> getAnimals() {
        return animals;
    }
}

