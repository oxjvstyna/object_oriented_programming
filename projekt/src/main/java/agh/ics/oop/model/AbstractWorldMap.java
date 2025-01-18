package agh.ics.oop.model;

import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;

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

    public void getReport() {
        String mostFrequentGenome = genotypes.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("BRAK");

        double averageEnergyForLiveAnimals = animals.stream()
                .filter(Animal::isAlive)
                .mapToInt(Animal::getEnergy)
                .average()
                .orElse(0.0);

        double averageDaysAliveForLiveAnimals = animals.stream()
                .filter(Animal::isAlive)
                .mapToInt(Animal::getAge)
                .average()
                .orElse(0.0);

        double averageChildrenForLiveAnimals = animals.stream()
                .filter(Animal::isAlive)
                .mapToInt(Animal::getNumberOfChildren)
                .average()
                .orElse(0.0);

        System.out.println("Liczba zwierzat na mapie: " + animals.size());
        System.out.println("Liczba roslin na mapie: " + plants.size());
        System.out.println("Liczba wolnych pol: " + (this.width * this.height - occupiedFields.size()));
        System.out.println("Najpopularniejszy genotyp: " + mostFrequentGenome);
        System.out.println("Sredni poziom energii dla zyjacych zwierzakow: " + averageEnergyForLiveAnimals);
        System.out.println("Sredni poziom dlugosci zycia zwierzakow na mapie: " + averageDaysAliveForLiveAnimals);
        System.out.println("Srednia liczba dzieci dla zyjacych zwierzakow: " + averageChildrenForLiveAnimals);
        System.out.println("Maksymalna ilosc zwierzat na mapie: " + this.maxAnimalSize);
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

    public String getCellContent(int x, int y) {
        Vector2d position = new Vector2d(x, y);
        boolean hasAnimals = hasAnimalAt(position);
        boolean hasPlants = plants.contains(position);

        if (hasAnimals && hasPlants) {
            int animalCount = occupiedFields.get(position).size();
            return "A" + animalCount + "P"; // np. "A3P" oznacza 3 zwierzęta i roślinę
        } else if (hasAnimals) {
            int animalCount = occupiedFields.get(position).size();
            return "A" + animalCount; // np. "A2" oznacza 2 zwierzęta
        } else if (hasPlants) {
            return "P"; // Tylko roślina
        } else {
            return "."; // Puste pole
        }
    }


    private boolean hasAnimalAt(Vector2d position) {
        return occupiedFields.containsKey(position) && !occupiedFields.get(position).isEmpty();
    }

}
