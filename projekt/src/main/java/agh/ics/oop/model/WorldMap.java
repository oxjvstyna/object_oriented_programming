package agh.ics.oop.model;

import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.IncorrectPositionException;

import java.util.List;
import java.util.UUID;

public interface WorldMap<T, P> extends MoveValidator {

    void place(T object) throws IncorrectPositionException;

    void move(T object, MoveDirection direction);

    boolean isOccupied(P position);

    WorldElement objectAt(P position);

    List<WorldElement> getElements();

}