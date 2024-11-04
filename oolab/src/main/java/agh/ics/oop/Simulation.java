package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<Animal> animals = new ArrayList<>();
    private final List<MoveDirection> moves = new ArrayList<>();
    public Simulation(List<Vector2d> positions, List<MoveDirection> moves) {
        for(Vector2d position : positions){
            this.animals.add(new Animal(position));
        }
        this.moves.addAll(moves);
    }
    public void run(){
        int animalCount = animals.size();
        int moveCount = moves.size();
        for(int i = 0; i < moveCount; i++){
            animals.get(i % animalCount).move(moves.get(i));
            System.out.println("Zwierze " + (i % animalCount + 1) + " : " + animals.get(i % animalCount).toString());
        }
    }

    public List<Animal> getAnimals() {
        return animals;
    }
}
