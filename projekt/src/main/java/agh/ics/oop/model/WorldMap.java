package agh.ics.oop.model;

import java.util.List;

public interface WorldMap<T, P> extends MoveValidator {

    void place(T object);

    void move(T object);

    WorldElement objectAt(P position);

    List<WorldElement> getElements();
}