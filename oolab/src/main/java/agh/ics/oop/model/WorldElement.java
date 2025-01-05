package agh.ics.oop.model;

public interface WorldElement {
    String getTextureResource();
    boolean isAt(Vector2d position); //Czy to na pewno tu musi być?
    Vector2d getPosition();
    String toString();
}
