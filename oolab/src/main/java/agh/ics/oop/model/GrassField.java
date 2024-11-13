package agh.ics.oop.model;

import java.util.Random;

public class GrassField implements WorldMap<Grass, Vector2d> {

    private int grassCount;

    public GrassField(int grassCount) {
        this.grassCount = grassCount;
    }

    @Override
    public boolean place(Grass grass) {
        return false;
    }

    @Override
    public void move(Grass grass, MoveDirection direction) {

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return null;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }

    public generateGrass(int grassCount){
        for(int i = 0; i < grassCount; i++){
            int n = grassCount;
            Grass newGrass = new Grass(new Vector2d(new (Random((long) Math.sqrt(grassCount * 10))))
            place()
        }
    }
}
