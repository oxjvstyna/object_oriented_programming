package agh.ics.oop.model;

public class GlobeMap extends AbstractWorldMap {


    public GlobeMap(int width, int height, GrowthVariant growthVariant, MoveVariant moveVariant) {
        super(width, height, growthVariant, moveVariant);
    }

    public Vector2d handleBorder(Animal animal) {
        Vector2d position = animal.getPosition();
        int x = position.x();
        int y = position.y();

        if (y < lowerLeft.y()) {
            y = lowerLeft.y();
            animal.reverseDirection();
            animal.setPosition(new Vector2d(x, y));
        }
        else if (y > upperRight.y()) {
            y = upperRight.y();
            animal.reverseDirection();
            animal.setPosition(new Vector2d(x, y));
        }

        if (x < lowerLeft.x()) {
            x = upperRight.x();
        }
        else if (x > upperRight.x()) {
            x = lowerLeft.x();
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

