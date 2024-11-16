package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation<T, P> {
    private final List<Animal> animals;
    private final List<MoveDirection> moves;
    private final WorldMap<T, P> currentMap;
    public Simulation(List<Vector2d> positions, List<MoveDirection> moves, WorldMap<T, P> currentMap) {
        this.animals = new ArrayList<>();
        this.currentMap = currentMap;
        this.moves = moves;
        for(Vector2d position : positions){
            Animal newAnimal = new Animal(position);
            if(currentMap.place((T) newAnimal)){
                animals.add(newAnimal);
            }
        }
        if(currentMap instanceof GrassField){
            ((GrassField) currentMap).generateGrass();
        }
    }
    public void run(){
        int animalCount = animals.size();
        int moveCount = moves.size();
        System.out.println(currentMap);
        for (int i = 0; i < moveCount; i++) {
            currentMap.move((T) animals.get(i % animalCount), moves.get(i));
            System.out.println(currentMap);
        }
    }

}
