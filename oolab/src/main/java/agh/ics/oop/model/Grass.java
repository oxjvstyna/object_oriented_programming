package agh.ics.oop.model;

public class Grass implements WorldElement {
    private final Vector2d position;
    public Grass(Vector2d position){
        this.position = position;
    }

    @Override
    public String getTextureResource() {
        return "grass.png";
    }


    @Override
    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public Vector2d getPosition() {
        return position;
    }

    public String toString(){
        return "*";
    }

}
