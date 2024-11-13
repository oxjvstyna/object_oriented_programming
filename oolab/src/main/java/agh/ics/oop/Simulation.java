package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<Animal> animals;
    private List<MoveDirection> moves = new ArrayList<>();
    private final RectangularMap currentMap;
    public Simulation(List<Vector2d> positions, List<MoveDirection> moves, RectangularMap currentMap) {
        this.animals = new ArrayList<>();
        this.currentMap = currentMap;
        this.moves = moves;

        for(Vector2d position : positions){
            Animal newAnimal = new Animal(position);
            if(currentMap.place(newAnimal)){
                animals.add(newAnimal);
            }
        }
    }
    public void run(){
        int animalCount = animals.size();
        int moveCount = moves.size();
        System.out.println(currentMap);
        for (int i = 0; i < moveCount; i++) {
            currentMap.move(animals.get(i % animalCount), moves.get(i));
            System.out.println(currentMap);
        }
    }

}
