package agh.ics.oop.model;

import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, // Identyfikacja typu po nazwie
        include = JsonTypeInfo.As.PROPERTY, // Przechowuj typ jako właściwość JSON
        property = "type" // Nazwa właściwości w JSON dla typu
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GlobeMap.class, name = "GlobeMap"),
        @JsonSubTypes.Type(value = OwlbearMap.class, name = "OwlbearMap")
})

public abstract class AbstractWorldMap implements WorldMap<Animal, Vector2d> {
    private final AnimalTracker tracker = new AnimalTracker();
    protected GrowthVariant growthVariant;
    protected MoveVariant moveVariant;
    protected final Map<Vector2d, List<Animal>> occupiedFields = new HashMap<>();
    protected final Set<Animal> animals = new HashSet<>();
    protected final Set<Vector2d> plants = new HashSet<>();
    protected final Map<String, Integer> genotypes = new HashMap<>();
    protected final List<MapChangeListener> observers = new ArrayList<>();
    protected Set<Vector2d> preferredFields;
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected int width;
    protected int height;
    protected int plantEnergy;
    int maxAnimalSize = 0;
    protected AnimalConfig config;


    public AbstractWorldMap(int width, int height, GrowthVariant growthVariant, AnimalConfig config) {
        this.growthVariant = growthVariant;
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width - 1, height - 1);
        this.width = width;
        this.height = height;
        this.preferredFields = growthVariant.generateFields();
        this.plantEnergy = 2;
        this.config = config;
    }

    public AbstractWorldMap() {
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
        List<Animal> toRemove = new ArrayList<>();
        for (Animal animal : animals) {
            if (!animal.isAlive()) {
                toRemove.add(animal);
            }
        }
        for (Animal animal : toRemove) {
            animals.remove(animal);
            List<Animal> animalsAtPosition = occupiedFields.get(animal.getPosition());
            if (animalsAtPosition != null) {
                animalsAtPosition.remove(animal);
                if (animalsAtPosition.isEmpty()) {
                    occupiedFields.remove(animal.getPosition());
                }
            }
        }
    }


    protected Animal resolveClash(List<Animal> animals) {
        return animals.stream()
                .max(Comparator.comparingInt(Animal::getEnergy)
                        .thenComparingInt(Animal::getAge)
                        .thenComparingInt(Animal::getNumberOfChildren)
                        .thenComparing(a -> Math.random()))
                .orElse(null);
    }


    protected void initializeAnimals(int animalCount) {
        RandomPositionGenerator positionGenerator = new RandomPositionGenerator(width, height, lowerLeft.x(), lowerLeft.y(), animalCount);
            positionGenerator.forEach(position -> {
                try {
                    this.place(new Animal(position, config));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
    }

    protected void consumePlants() {
        for (List<Animal> field : occupiedFields.values()){

            if (field == null || field.isEmpty()) {
                continue;
            }

            Animal highestPriorityAnimal = resolveClash(field);
            Vector2d position = highestPriorityAnimal.getPosition();
            if (plants.contains(position)){
                highestPriorityAnimal.addEnergy(plantEnergy);
                plants.remove(position);
                tracker.onPlantEaten();

            }
        }
    }

    protected void moveAnimals() {
        for (Animal animal : animals) {
            this.move(animal);
        }
    }

    protected void reproduceAnimals() {
        for (List<Animal> field : occupiedFields.values()) {
            if (field.size() > 1) {

                Animal parent1 = resolveClash(field);
                field.remove(parent1);
                Animal parent2 = resolveClash(field);
                field.add(parent1);

                if (parent1.getEnergy() >= parent1.birthEnergy && parent2.getEnergy() >= parent2.birthEnergy) {
                    Animal child = parent1.reproduce(parent2);
                    animals.add(child);
                    this.place(child);
                    tracker.onDescendantAdded();

                }
            }
        }
    }

    public void initializeMap(int animalCount) {
        initializeAnimals(animalCount);
    }

    public void handleMap() {
        removeAnimals();
        moveAnimals();
        consumePlants();
        reproduceAnimals();
        growPlants();
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
    public void move(Animal animal) {
        Vector2d initialPosition = animal.getPosition();
        occupiedFields.get(initialPosition).remove(animal);

        animal.move(this);
        Vector2d newPosition = adjustPosition(animal);

        occupiedFields.putIfAbsent(newPosition, new ArrayList<>());
        occupiedFields.get(newPosition).add(animal);

        notifyObservers("Zwierze ruszylo z " + initialPosition + " do " + newPosition);
    }


    @Override
    public void place(Animal animal) {
        Vector2d position = animal.getPosition();
        animals.add(animal);
        genotypes.merge(Arrays.toString(animal.getGenomes().getGenesAsStrings()), 1, Integer::sum);
        occupiedFields.putIfAbsent(position, new ArrayList<>());
        occupiedFields.get(position).add(animal);

        maxAnimalSize = Math.max(maxAnimalSize, animals.size());

        notifyObservers("Zwierze umieszczone na pozycji " + position);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.precedes(upperRight)) &&
                (position.follows(lowerLeft));
    }


    @Override
    public List<WorldElement> getElements() {
        return new ArrayList<>(animals);
    }

    @Override
    public List<Animal> getOrderedAnimals() {
        List<Animal> sortedAnimals = new ArrayList<>(animals);
        return sortedAnimals.stream()
                .sorted(Comparator.comparing((Animal animal) -> animal.getPosition().x())
                        .thenComparing(animal -> animal.getPosition().y()))
                .toList();
    }


    public void setPlantEnergy(int plantEnergy) {
        this.plantEnergy = plantEnergy;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean hasPlantAt(int x, int y) {
        return plants.contains(new Vector2d(x, y));
    }

    private boolean hasAnimalAt(Vector2d position) {
        return occupiedFields.containsKey(position) && !occupiedFields.get(position).isEmpty();
    }

    public int getMaxEnergyAt(int x, int y) {
        Vector2d position = new Vector2d(x, y);
        if (occupiedFields.containsKey(position) && !occupiedFields.get(position).isEmpty()) {
            return occupiedFields.get(position).stream()
                    .mapToInt(Animal::getEnergy)  // Pobieramy energię każdego zwierzęcia na tej pozycji
                    .max()  // Zwracamy maksymalną wartość energii
                    .orElse(0);  // Jeśli nie ma zwierząt, zwrócimy 0
        }
        return 0;  // Jeśli nie ma zwierząt na tej pozycji
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public Map<String, Integer> getGenotypes() {
        return genotypes;
    }

    public Collection<Animal> getAnimals() {
        return animals;
    }

    public Map<Vector2d, List<Animal>> getOccupiedFields() {
        return occupiedFields;
    }

    public Set<Vector2d> getPlants() {
        return plants;
    }

    public int getMaxAnimalSize() {
        return maxAnimalSize;
    }

    public Animal getAnimalAt(int x, int y) {
        Vector2d position = new Vector2d(x, y);
        if (occupiedFields.containsKey(position) && !occupiedFields.get(position).isEmpty()) {
            return occupiedFields.get(position).getFirst();
        }
        return null;
    }
}
