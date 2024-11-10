package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simulation {
    private final Map<Vector2d, Animal> animals;
    private final List<MoveDirection> moves = new ArrayList<>();
    private final RectangularMap currentMap;
    public Simulation(List<Vector2d> positions, List<MoveDirection> moves, RectangularMap currentMap) {
        this.moves.addAll(moves);
        this.currentMap = currentMap;
        animals = currentMap.getAnimals();
        for(Vector2d position : positions){
            Animal newAnimal = new Animal(position);
            currentMap.place(newAnimal);
        }
    }
    public void run(){
        List<Animal> animalList = new ArrayList<>(animals.values());
        int animalCount = animalList.size();
        int moveCount = moves.size();
        System.out.println(currentMap);
        for (int i = 0; i < moveCount; i++) {
            currentMap.move(animalList.get(i % animalCount), moves.get(i));
            System.out.println(currentMap);
        }
    }

}
