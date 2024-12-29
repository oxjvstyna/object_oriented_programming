package agh.ics.oop.model;

import java.util.Map;

public class EarthMap extends AbstractWorldMap {


    public EarthMap(int width, int height, GrowthVariant growthVariant) {
        super(width, height, growthVariant);
    }

    public Vector2d handleBorder(Vector2d position, Animal animal) {
        int x = position.getX();
        int y = position.getY();

        if (x < lowerLeft.getX()) {
            x = upperRight.getX();
        } else if (x > upperRight.getX()) {
            x = lowerLeft.getX();
        }

        if (y < lowerLeft.getY() || y > upperRight.getY()) {
            animal.reverseDirection();
            return position;
        }

        return new Vector2d(x, y);
    }

    public Map<Vector2d, Animal> getAnimals() {
        return animals;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    @Override
    protected Vector2d adjustPosition(Vector2d position, Animal animal) {
        return handleBorder(position, animal);
    }
}

