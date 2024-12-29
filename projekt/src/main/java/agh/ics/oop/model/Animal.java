package agh.ics.oop.model;

import java.util.List;

public class Animal implements WorldElement {
    private final int REPRODUCTION_ENERGY;
    private final int minMutation;
    private final int maxMutation;
    private Vector2d position;
    private MapDirection orientation;
    private int energy;
    private Genome genome;
    public Animal parent1;
    public Animal parent2;
    private int birthEnergy;

    public Animal(Vector2d initialPosition, int initialEnergy, int genomeLength, int reproductionEnergy, int birthEnergy, int minMutation, int maxMutation) {
        this.orientation = MapDirection.NORTH; //randomowa
        this.position = initialPosition;
        this.energy = initialEnergy;
        this.genome = new Genome(genomeLength);
        this.parent1 = null;
        this.parent2 = null;
        this.REPRODUCTION_ENERGY = reproductionEnergy;
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;
    }

    //tworzenie dzieci
    public Animal(Vector2d position, int energy, Genome genome, Animal parent1, Animal parent2, int reproductionEnergy, int birthEnergy, int minMutation, int maxMutation) {
        this.orientation = MapDirection.NORTH; //zmienic na randomową
        this.position = position;
        this.energy = energy;
        this.genome = genome;
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.REPRODUCTION_ENERGY = reproductionEnergy;
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;

    }

    public Animal reproduce(Animal parent1, Animal parent2) {

        List<Integer> childGenes = parent1.genome.createChildGenome(parent1, parent2);

        Genome childGenome = new Genome(childGenes, parent1.minMutation, parent1.maxMutation);

        parent1.changeEnergy(-parent1.birthEnergy);
        parent2.changeEnergy(-parent2.birthEnergy);


        return new Animal(parent1.position,2 * parent1.birthEnergy, childGenome, parent1, parent2, parent1.REPRODUCTION_ENERGY, parent1.birthEnergy, parent1.minMutation, parent1.maxMutation);
    }

    public void move(int direction, MoveValidator validator) {
        switch (direction) {
            case 0:
                break;
            case 1:
                this.orientation = this.orientation.next();
                break;
            case 2:
                this.orientation = this.orientation.next().next();
                break;
            case 3:
                this.orientation = this.orientation.next().next().next();
                break;
            case 4:
                this.orientation = this.orientation.next().next().next().next();
                break;
            case 5:
                this.orientation = this.orientation.previous().previous().previous();
                break;
            case 6:
                this.orientation = this.orientation.previous().previous();
                break;
            case 7:
                this.orientation = this.orientation.previous();
                break;
            default:
                throw new IllegalArgumentException("Invalid move direction: " + direction);
        }

        Vector2d movementVector = this.orientation.toUnitVector();
        Vector2d nextPosition = this.position.add(movementVector);

        if (validator.canMoveTo(nextPosition)) {
            this.position = nextPosition;
        }
    }

    public void changeEnergy(int value) {
        this.energy += value;
        if (this.energy < 0) {
            this.energy = 0;
        }
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
}
