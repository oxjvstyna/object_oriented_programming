package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private final List<Animal> animals;
    private final List<MoveDirection> moves;
    private final AbstractWorldMap currentMap;
    public Simulation(List<Vector2d> positions, List<MoveDirection> moves, AbstractWorldMap currentMap) {
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
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println("Symulacja przerwana: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
