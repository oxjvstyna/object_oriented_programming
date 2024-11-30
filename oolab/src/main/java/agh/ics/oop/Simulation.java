package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<Animal> animals;
    private final List<MoveDirection> moves;
    private final WorldMap<Animal, Vector2d> currentMap;
    public Simulation(List<Vector2d> positions, List<MoveDirection> moves, WorldMap<Animal, Vector2d> currentMap) {
        this.animals = new ArrayList<>();
        this.currentMap = currentMap;
        this.moves = moves;
        for(Vector2d position : positions){
            Animal newAnimal = new Animal(position);
            try{
                currentMap.place(newAnimal);
                animals.add(newAnimal);
            }catch(IncorrectPositionException e){
                System.out.println("Uwaga: " + e.getMessage());
            }

        }

    }

    public void run(){
        int animalCount = animals.size();
        int moveCount = moves.size();
        for (int i = 0; i < moveCount; i++) {
            currentMap.move(animals.get(i % animalCount), moves.get(i));
        }
    }

}
