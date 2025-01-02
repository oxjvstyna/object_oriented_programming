package agh.ics.oop.model;

import java.util.List;
import java.util.Map;

public class EarthMap extends AbstractWorldMap {


    public EarthMap(int width, int height, GrowthVariant growthVariant) {
        super(width, height, growthVariant);
    }

    public Vector2d handleBorder(Animal animal) {
        Vector2d position = animal.getPosition();
        int x = position.getX();
        int y = position.getY();

        if (y < lowerLeft.getY()) {
            y = lowerLeft.getY();
            animal.reverseDirection();
            animal.setPosition(new Vector2d(x, y));
        }
        else if (y > upperRight.getY()) {
            y = upperRight.getY();
            animal.reverseDirection();
            animal.setPosition(new Vector2d(x, y));
        }

        if (x < lowerLeft.getX()) {
            x = upperRight.getX();
        }
        else if (x > upperRight.getX()) {
            x = lowerLeft.getX();
        }


        animal.setPosition(new Vector2d(x, y));
        return new Vector2d(x, y);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    @Override
    protected Vector2d adjustPosition(Animal animal) {
        return handleBorder(animal);
    }
}

