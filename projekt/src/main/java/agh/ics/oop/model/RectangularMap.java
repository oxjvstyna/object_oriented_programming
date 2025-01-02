package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;

import java.util.List;
import java.util.Map;

public class RectangularMap extends AbstractWorldMap {

    public RectangularMap(int width, int height, Vector2d lowerLeft, Vector2d upperRight, GrowthVariant growthVariant) {
        super(width, height, growthVariant);
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }
}

