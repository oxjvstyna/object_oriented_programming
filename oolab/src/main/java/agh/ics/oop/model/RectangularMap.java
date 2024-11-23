package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;

import java.util.List;
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

    @Override
    public Boundary getCurrentBounds(){
        return new Boundary(new Vector2d(leftWidth, leftHeight), new Vector2d(rightWidth, rightHeight));
    }
}

