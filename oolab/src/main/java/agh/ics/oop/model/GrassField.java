package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;

public class GrassField implements WorldMap<Grass, Vector2d> {

    private final int grassCount;
    private final Map<Vector2d, Grass> grasses = new HashMap<>();
    private int width;
    private int height;

    public GrassField(int grassCount) {
        this.grassCount = grassCount;
    }

    @Override
    public String toString() {
        MapVisualizer toDraw = new MapVisualizer(this);
        return toDraw.draw(new Vector2d(0, 0), new Vector2d(width, height));
    }

    @Override
    public boolean place(Grass grass) {
        if(canMoveTo(grass.getPosition())){
            grasses.put(grass.getPosition(), grass);
            return true;
        }
        return false;
    }

    @Override
    public void move(Grass grass, MoveDirection direction) {

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return grasses.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return grasses.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.precedes(new Vector2d(width, height))) &&
                (position.follows(new Vector2d(0, 0))) &&
                !isOccupied(position);
    }

    public void generateGrass(){
        for(int i = 0; i < grassCount; i++){
            int r1 = (int) (Math.random() * Math.sqrt(grassCount * 10) + 1);
            int r2 = (int) (Math.random() * Math.sqrt(grassCount * 10) + 1);
            if(r1 > width){
                width = r1;
            }
            if(r2 > height){
                height = r2;
            }
            Vector2d position = new Vector2d(r1, r2);
            if (isOccupied(position)){
                i--;
            }
            Grass newGrass = new Grass(position);
            this.place(newGrass);
        }
    }
}