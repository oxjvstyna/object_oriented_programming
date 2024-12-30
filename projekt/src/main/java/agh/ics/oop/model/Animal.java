package agh.ics.oop.model;

import java.util.List;
import java.util.Map;

public class Animal implements WorldElement {
    private final int reproductionEnergy;
    private final int minMutation;
    private final int maxMutation;
    private Vector2d position;
    MapDirection orientation;
    private int energy;
    private final Genome genome;
    public Animal parent1;
    public Animal parent2;
    int birthEnergy;

    public Animal(Vector2d initialPosition, int initialEnergy, int genomeLength, int reproductionEnergy, int birthEnergy, int minMutation, int maxMutation) {
        this.orientation = MapDirection.NORTH; //randomowa
        this.position = initialPosition;
        this.energy = initialEnergy;
        this.genome = new Genome(genomeLength);
        this.parent1 = null;
        this.parent2 = null;
        this.reproductionEnergy = reproductionEnergy;
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
        this.reproductionEnergy = reproductionEnergy;
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;

    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public Animal reproduce(Animal parent1, Animal parent2) {

        List<Integer> childGenes = parent1.genome.createChildGenome(parent1, parent2);

        Genome childGenome = new Genome(childGenes, parent1.minMutation, parent1.maxMutation);

        parent1.changeEnergy(-parent1.birthEnergy);
        parent2.changeEnergy(-parent2.birthEnergy);


        return new Animal(parent1.position,2 * parent1.birthEnergy, childGenome, parent1, parent2, parent1.reproductionEnergy, parent1.birthEnergy, parent1.minMutation, parent1.maxMutation);
    }

    public void move(MoveDirection direction, MoveValidator validator) {
        switch (direction) {
            case FORWARD:
                Vector2d nextPositionForward = this.position.add(orientation.toUnitVector());
                if(validator.canMoveTo(nextPositionForward)) {
                    this.position = nextPositionForward;
                }
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
                Vector2d nextPositionBackward = this.position.subtract(orientation.toUnitVector());
                if(validator.canMoveTo(nextPositionBackward)) {
                    this.position = nextPositionBackward;
                }
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
