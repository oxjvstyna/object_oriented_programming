package agh.ics.oop.model;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap<Animal, Vector2d> {
    protected GrowthVariant growthVariant;
    protected MoveVariant moveVariant;
    protected final Map<Vector2d, List<Animal>> occupiedFields = new HashMap<>();
    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final Set<Vector2d> plants = new HashSet<>();
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected int width;
    protected int height;
    protected int plantEnergy;
    protected int animalConfig;
    protected final List<MapChangeListener> observers = new ArrayList<>();
    protected Set<Vector2d> preferredFields;

    public AbstractWorldMap(int width, int height, GrowthVariant growthVariant, MoveVariant moveVariant) {
        this.growthVariant = growthVariant;
        this.moveVariant = moveVariant;
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width - 1, height - 1);
        this.width = width;
        this.height = height;
        this.preferredFields = growthVariant.generateFields();
    }

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

    protected void removeAnimals() {
        for (List<Animal> field : occupiedFields.values()) {
            for (Animal animal : field) {
                animals.remove(animal.getPosition());
                field.remove(animal);
            }
        }
    }

    protected void handleClash(Animal animal1, Animal animal2) {
        // tu cos bedzie...
    }

    protected void initializeAnimals(int animalCount) {
        RandomPositionGenerator positionGenerator = new RandomPositionGenerator(width, height, lowerLeft.getX(), lowerLeft.getY(), animalCount);
            positionGenerator.forEach(position -> {
                try {
                    this.place(new Animal(position, 100, 10, 10, 10, 10, 10, moveVariant));
                } catch (IncorrectPositionException e) {
                    throw new RuntimeException(e);
                }
            });

    }

    protected void consumePlants() {
        // To napraw zeby handlowalo clashe
        for (Animal animal : animals.values()) {
            Vector2d position = animal.getPosition();
            if (plants.contains(position)) {
                animal.addEnergy(plantEnergy);
                plants.remove(position);
            }
        }
    }

    protected void moveAnimals() {
        for (Animal animal : animals.values()) {

            String[] genomeAsStrings = animal.getGenomes().getGenesAsStrings();
            List<MoveDirection> moves = OptionsParser.parse(genomeAsStrings);

            // do zmiany
            Random random = new Random();
            int randomNumber = random.nextInt(moves.size()) + 1;

            this.move(animal, moves.get(randomNumber));
        }
    }

    protected void reproduceAnimals() {
        for (List<Animal> field : occupiedFields.values()) {
            for (Animal animal : field) {
                continue;
            }
        }
    }

    public void initializeMap(int animalCount) {
        initializeAnimals(animalCount);
    }

    public void handleMap() {
//        removeAnimals();
        moveAnimals();
//        consumePlants();
//        reproduceAnimals();
//        growPlants();
    }


    protected Vector2d adjustPosition(Animal animal) {
        return animal.getPosition();
    }

    protected void placePlant(Vector2d position) {
        plants.add(position);
    }

    public void growPlants() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Vector2d position = new Vector2d(x, y);

                boolean isFertile = preferredFields.contains(position);

                double growthChance = isFertile ? 0.8 : 0.2;
                if (Math.random() < growthChance) {
                    placePlant(position);
                }
            }
        }
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d initialPosition = animal.getPosition();
        occupiedFields.get(initialPosition).remove(animal);
        animals.remove(initialPosition);

        animal.move(direction, this);
        Vector2d newPosition = adjustPosition(animal);

        animals.put(newPosition, animal);
        occupiedFields.putIfAbsent(newPosition, new ArrayList<>());
        occupiedFields.get(newPosition).add(animal);

        notifyObservers("Zwierze ruszylo z " + initialPosition + " do " + newPosition);
    }


    @Override
    public void place(Animal animal) throws IncorrectPositionException {
        Vector2d position = animal.getPosition();
        if (canMoveTo(position)) {
            animals.put(position, animal);
            occupiedFields.putIfAbsent(position, new ArrayList<>());
            occupiedFields.get(position).add(animal);
            notifyObservers("Zwierze umieszczone na pozycji " + position);
        } else {
            throw new IncorrectPositionException(position);
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.precedes(upperRight)) &&
                (position.follows(lowerLeft));
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public List<WorldElement> getElements() {
        return new ArrayList<>(animals.values());
    }



}
