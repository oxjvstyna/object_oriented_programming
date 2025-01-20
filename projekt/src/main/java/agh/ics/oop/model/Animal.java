package agh.ics.oop.model;

import agh.ics.oop.OptionsParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Animal implements WorldElement {
    private static int idCounter = 0;
    private final int id;
    private final int reproductionEnergy;
    private final int minMutation;
    private final int maxMutation;
    private int moveIndex;
    private Vector2d position;
    MapDirection orientation;
    private int energy;
    private final Genome genome;
    public Animal parent1;
    public Animal parent2;
    int birthEnergy;
    private final MoveVariant moveVariant;
    private int age = 0;
    private int deathDay = 0;
    private int numberOfChildren = 0;
    private int plantsEaten = 0;
    private int descendantsCount = 0;
    private final List<Animal> children = new ArrayList<>();

    public void addPlantsEaten() {
        this.plantsEaten = plantsEaten + 1;
    }

    public int getPlantsEaten() {
        return plantsEaten;
    }

    public void addDescendantsCount() {
        this.descendantsCount = descendantsCount + 1;
    }

    public List<Animal> getChildren() {
        return children;
    }

    public void addChild(Animal child) {
        children.add(child);
    }

    public Animal(Vector2d initialPosition, AnimalConfig config) {
        this.id = idCounter++;
        this.orientation = getRandomOrientation();
        this.position = initialPosition;
        this.energy = config.initialEnergy();
        this.genome = new Genome(config.genomeLength());
        this.parent1 = null;
        this.parent2 = null;
        this.reproductionEnergy = config.reproductionEnergy();
        this.minMutation = config.minMutation();
        this.maxMutation = config.maxMutation();
        this.moveVariant = config.moveVariant();
        this.birthEnergy = config.birthEnergy();
        this.moveIndex = -1;
    }

    // Dodawanie dziecka
    public Animal(Vector2d position, int energy, Genome genome, Animal parent1, Animal parent2, int reproductionEnergy, int birthEnergy, int minMutation, int maxMutation, int moveIndex, MoveVariant moveVariant) {
        this.id = idCounter++;
        this.moveIndex = moveIndex;
        this.orientation = getRandomOrientation();
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

    private MapDirection getRandomOrientation() {
        MapDirection[] directions = MapDirection.values();
        Random random = new Random();
        return directions[random.nextInt(directions.length)];
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public Animal reproduce(Animal parent) {
        this.numberOfChildren++;
        parent.setNumberOfChildren(parent.getNumberOfChildren() + 1);

        List<Integer> childGenes = this.genome.createChildGenome(this, parent);
        Genome childGenome = new Genome(childGenes, this.minMutation, this.maxMutation);

        Animal child = new Animal(this.position, this.birthEnergy + parent.birthEnergy, childGenome, this, parent, this.reproductionEnergy, this.birthEnergy, this.minMutation, this.maxMutation, -1, moveVariant);
        this.addEnergy(-this.birthEnergy);
        parent.addEnergy(-parent.birthEnergy);
        return child;
    }

    public void move(MoveValidator validator) {
        this.age += 1;
        int index = this.moveVariant.getNextMoveIndex(genome, moveIndex);
        this.moveIndex = index;
        int moveDirectionCode = genome.getGenes().get(index);
        List<MoveDirection> directions = OptionsParser.parse(new String[]{Integer.toString(moveDirectionCode)});
        MoveDirection direction = directions.getFirst();
        switch(direction) {
            case FORWARD:
                break;
            case FORWARD_RIGHT:
            case RIGHT:
            case BACKWARD_RIGHT:
            case BACKWARD:
                this.orientation = this.orientation.next(direction.ordinal());
                break;
            case BACKWARD_LEFT:
            case LEFT:
            case FORWARD_LEFT:
                this.orientation = this.orientation.previous(8 - direction.ordinal());
                break;
            default:
            throw new IllegalArgumentException("Invalid move direction: " + direction);
    }


        Vector2d nextPositionForward = this.position.add(orientation.toUnitVector());
        if (validator.canMoveTo(nextPositionForward)) {
            this.position = nextPositionForward;
        }
        this.addEnergy(-1);
    }

    public void updateDescendantsCount() {
        this.descendantsCount++;
        if (this.parent1 != null) {
            this.parent1.updateDescendantsCount();
        }
}

    // Getter for descendantsCount
    public int getDescendantsCount() {
        return this.descendantsCount;
    }

    public void addEnergy(int value) {
        this.energy += value;
    }

    public int getEnergy() {
        return this.energy;
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

    public int getAge() {
        return age;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }


    public int getMoveIndex() {
        return this.moveIndex;
    }

    public Genome getGenomes() {
        return this.genome;
    }

    public int getId() {
        return this.id;
    }

    public int getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(int deathDay) {
        this.deathDay = deathDay;
    }

    public Genome getGenome() {
        return this.genome;
    }
}
