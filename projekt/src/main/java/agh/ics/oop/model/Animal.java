package agh.ics.oop.model;

import agh.ics.oop.OptionsParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Animal implements WorldElement {
    private final int reproductionEnergy;
    private final int minMutation;
    private final int maxMutation;
    private final int moveIndex;
    private Vector2d position;
    MapDirection orientation;
    private int energy;
    private final Genome genome;
    public Animal parent1;
    public Animal parent2;
    int birthEnergy;
    private MoveVariant moveVariant;

    public Animal(Vector2d initialPosition, int initialEnergy, int genomeLength, int reproductionEnergy, int birthEnergy, int minMutation, int maxMutation, MoveVariant moveVariant) {
        this.orientation = MapDirection.NORTH; //randomowa
        this.position = initialPosition;
        this.energy = initialEnergy;
        this.genome = new Genome(genomeLength);
        this.parent1 = null;
        this.parent2 = null;
        this.reproductionEnergy = reproductionEnergy;
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;
        this.moveIndex = -1;
        this.moveVariant = moveVariant;
        this.birthEnergy = birthEnergy;
    }

    //tworzenie dzieci
    public Animal(Vector2d position, int energy, Genome genome, Animal parent1, Animal parent2, int reproductionEnergy, int birthEnergy, int minMutation, int maxMutation, int moveIndex, MoveVariant moveVariant) {
        this.moveIndex = moveIndex;
        this.orientation = MapDirection.NORTH; //zmienic na randomową
        this.position = position;
        this.energy = energy;
        this.genome = genome;
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.reproductionEnergy = reproductionEnergy;
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;
        this.moveVariant = moveVariant;
        this.birthEnergy = birthEnergy;

    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public Animal reproduce(Animal parent) {
        List<Integer> childGenes = this.genome.createChildGenome(this, parent);

        if (childGenes == null || childGenes.isEmpty()) {
            throw new IllegalStateException("Child genome cannot be null or empty.");
        }

        Genome childGenome = new Genome(childGenes, this.minMutation, this.maxMutation);

        // Utwórz dziecko z poprawnym genomem i pozycją
        Animal child = new Animal(
                this.position,
                this.birthEnergy + parent.birthEnergy,
                childGenome,
                this,
                parent,
                this.reproductionEnergy,
                this.birthEnergy,
                this.minMutation,
                this.maxMutation,
                -1,
                moveVariant
        );

        this.addEnergy(-this.birthEnergy);
        parent.addEnergy(-parent.birthEnergy);

        return child;
    }


    public void move(MoveValidator validator) {
        int index = this.moveVariant.getNextMoveIndex(genome, moveIndex);
        int moveDirectionCode = genome.getGenes().get(index);
        List<MoveDirection> directions = OptionsParser.parse(new String[]{Integer.toString(moveDirectionCode)});
        MoveDirection direction = directions.getFirst();
        switch (direction) {
            case FORWARD:
                break;
            case FORWARD_RIGHT:
                this.orientation = this.orientation.next();
                break;
            case RIGHT:
                this.orientation = this.orientation.next().next();
                break;
            case BACKWARD_RIGHT:
                this.orientation = this.orientation.next().next().next();
                break;
            case BACKWARD:
                this.orientation = this.orientation.next().next().next().next();
                break;
            case BACKWARD_LEFT:
                this.orientation = this.orientation.previous().previous().previous();
                break;
            case LEFT:
                this.orientation = this.orientation.previous().previous();
                break;
            case FORWARD_LEFT:
                this.orientation = this.orientation.previous();
                break;
            default:
                throw new IllegalArgumentException("Invalid move direction: " + direction);
        }
        Vector2d nextPositionForward = this.position.add(orientation.toUnitVector());
        if(validator.canMoveTo(nextPositionForward)) {
            this.position = nextPositionForward;
        }
        this.addEnergy(-1); // ale czy nie ma odejmowac energii tylko po wykonaniu ruchu?
    }


    public void addEnergy(int value) {
        this.energy += value;
    }

    public int getEnergy() {
        return this.energy;
    }

    public Genome getGenomes() {
        return this.genome;
    }

    public boolean isAlive() {
        return energy > 0;
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public MapDirection getOrientation() {
        return this.orientation;
    }

    public void reverseDirection() {
        Map<MapDirection, MapDirection> opposites = Map.of(
                MapDirection.NORTH, MapDirection.SOUTH,
                MapDirection.SOUTH, MapDirection.NORTH,
                MapDirection.EAST, MapDirection.WEST,
                MapDirection.WEST, MapDirection.EAST,
                MapDirection.NORTH_EAST, MapDirection.SOUTH_WEST,
                MapDirection.NORTH_WEST, MapDirection.SOUTH_EAST,
                MapDirection.SOUTH_WEST, MapDirection.NORTH_EAST,
                MapDirection.SOUTH_EAST, MapDirection.NORTH_WEST
        );

        this.orientation = opposites.get(this.orientation);
    }
}
