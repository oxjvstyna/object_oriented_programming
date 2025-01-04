package agh.ics.oop.model;

import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap<Animal, Vector2d> {
    protected GrowthVariant growthVariant;
    protected MoveVariant moveVariant;
    protected final Map<Vector2d, List<Animal>> occupiedFields = new HashMap<>();
    protected final Set<Animal> animals = new HashSet<>();
    protected final Set<Vector2d> plants = new HashSet<>();
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected int width;
    protected int height;
    protected int plantEnergy;
    protected int animalConfig;
    protected final List<MapChangeListener> observers = new ArrayList<>();
    protected Set<Vector2d> preferredFields;
    int maxAnimalSize = 0;


    public AbstractWorldMap(int width, int height, GrowthVariant growthVariant, MoveVariant moveVariant) {
        this.growthVariant = growthVariant;
        this.moveVariant = moveVariant;
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width - 1, height - 1);
        this.width = width;
        this.height = height;
        this.preferredFields = growthVariant.generateFields();
        this.plantEnergy = 2;
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
        // Sortuj zwierzęta według priorytetów
        return animals.stream()
                .max(Comparator.comparingInt(Animal::getEnergy)             // Najwięcej energii
                        .thenComparingInt(Animal::getAge)                   // Najstarsze
                        .thenComparingInt(Animal::getNumberOfChildren)      // Najwięcej dzieci
                        .thenComparing(a -> Math.random()))                 // Losowość
                .orElse(null); // Zwróć zwierzę o najwyższym priorytecie
    }


    protected void initializeAnimals(int animalCount) {
        RandomPositionGenerator positionGenerator = new RandomPositionGenerator(width, height, lowerLeft.getX(), lowerLeft.getY(), animalCount);
            positionGenerator.forEach(position -> {
                try {
                    this.place(new Animal(position, 10, 5, 10, 10, 1, 4, moveVariant));
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
//                System.out.println("Energia wzrosla z " + highestPriorityAnimal.getEnergy() + " do " + (highestPriorityAnimal.getEnergy() + plantEnergy) );
                plants.remove(position);
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
            if (field.size() >= 2) {
                // Logika oczywiście do zmiany
                Animal parent1 = field.getFirst();
                Animal parent2 = field.getLast();
                if (parent1.getEnergy() >= parent1.birthEnergy && parent2.getEnergy() >= parent2.birthEnergy) {
                    Animal child = parent1.reproduce(parent2);
                    animals.add(child);
                    this.maxAnimalSize = Math.max(animals.size(), maxAnimalSize);
                    this.place(child);
                    return;
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
        System.out.println("Liczba zwierzat na mapie: " + animals.size());
        System.out.println("Liczba roslin na mapie: " + plants.size());
        System.out.println("Liczba wolnych pol: " + (this.width * this.height - occupiedFields.size()));
        System.out.println("Najpopularniejszy genotyp: " + "DO ZROBIENIA");
        System.out.println("Sredni poziom energii dla zyjacych zwierzakow: " + "DO ZROBIENIA");
        System.out.println("Sredni poziom dlugosci zycia zwierzakow na mapie: " + "DO ZROBIENIA");
        System.out.println("Srednia liczba dzieci dla zyjacych zwierzakow: " + "DO ZROBIENIA");
        System.out.println("Total animals: " + this.maxAnimalSize);
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
        occupiedFields.putIfAbsent(position, new ArrayList<>());
        occupiedFields.get(position).add(animal);
        notifyObservers("Zwierze umieszczone na pozycji " + position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.precedes(upperRight)) &&
                (position.follows(lowerLeft));
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        // na szybko do zmiany
        return new Plant(new Vector2d(position.getX(), position.getY()));
    }

    @Override
    public List<WorldElement> getElements() {
        return new ArrayList<>(animals);
    }



}
