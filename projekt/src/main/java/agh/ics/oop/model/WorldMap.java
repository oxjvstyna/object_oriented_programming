package agh.ics.oop.model;

import java.util.List;
import java.util.Optional;

public interface WorldMap<T, P> extends MoveValidator {

    void place(T object);

    void move(T object);

    List<WorldElement> getElements();

    List<Animal> getOrderedAnimals();
}