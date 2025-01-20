package agh.ics.oop.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AbstractWorldMapTest {

    private AbstractWorldMap worldMap;
    private AnimalConfig animalConfig;

    @BeforeEach
    void setUp() {
        animalConfig = new AnimalConfig(10, 5, 10, 10, 1, 4, new TotalPredestination());
        worldMap = new GlobeMap(10, 10, new FertileEquator(10, 10), animalConfig);
    }

    @Test
    void shouldRemoveDeadAnimals() {
        // given

        Animal aliveAnimal = new Animal(new Vector2d(1, 1), animalConfig);
        AnimalConfig deadAnimalConfig = new AnimalConfig(0, 5, 10, 10, 1, 4, new TotalPredestination());
        Animal deadAnimal = new Animal(new Vector2d(2, 2), deadAnimalConfig);

        worldMap.place(aliveAnimal);
        worldMap.place(deadAnimal);

        // when
        worldMap.removeAnimals();

        // then
        assertTrue(worldMap.getElements().contains(aliveAnimal));
        assertFalse(worldMap.getElements().contains(deadAnimal));
    }

    @Test
    void shouldMoveAnimalsToNewPositions() {
        // given
        Animal animal = new Animal(new Vector2d(1, 1), animalConfig);
        worldMap.place(animal);

        // when
        worldMap.moveAnimals();

        // then
        assertNotEquals(new Vector2d(1, 1), animal.getPosition());
    }

    @Test
    void shouldConsumePlantIfPresent() {
        // given
        Vector2d plantPosition = new Vector2d(2, 2);
        worldMap.placePlant(plantPosition);
        worldMap.setPlantEnergy(10);

        Animal animal = new Animal(plantPosition, animalConfig);
        worldMap.place(animal);

        // when
        worldMap.consumePlants();

        // then
        assertFalse(worldMap.plants.contains(plantPosition));
        assertTrue(animal.getEnergy() > 10);
    }


    @Test
    void shouldReproduceAnimalsIfConditionsMet() {
        // given
        Animal parent1 = new Animal(new Vector2d(3, 3), animalConfig);
        Animal parent2 = new Animal(new Vector2d(3, 3), animalConfig);
        worldMap.place(parent1);
        worldMap.place(parent2);

        // when
        worldMap.reproduceAnimals();

        // then
        assertTrue(worldMap.getElements().size() > 2);
    }

    @Test
    void shouldGrowPlantsWithProbability() {
        // given
        int initialPlantCount = worldMap.plants.size();

        // when
        worldMap.growPlants();

        // then
        assertTrue(worldMap.plants.size() > initialPlantCount);
    }

    @Test
    void testGetOrderedAnimals() {
        // given
        GrowthVariant growthVariant = new FertileEquator(10, 10);
        AbstractWorldMap map = getAbstractWorldMap(growthVariant);

        // then
        List<Animal> sortedAnimals = map.getOrderedAnimals();

        assertEquals(new Vector2d(1, 1), sortedAnimals.get(0).getPosition());
        assertEquals(new Vector2d(1, 2), sortedAnimals.get(1).getPosition());
        assertEquals(new Vector2d(2, 3), sortedAnimals.get(2).getPosition());
        assertEquals(new Vector2d(3, 0), sortedAnimals.get(3).getPosition());
    }

    private AbstractWorldMap getAbstractWorldMap(GrowthVariant growthVariant) {
        AnimalConfig animalConfig = new AnimalConfig(10, 5, 10, 10, 1, 4, new TotalPredestination());
        AbstractWorldMap map = new GlobeMap(10, 10, growthVariant, animalConfig);

        // when
        Animal animal1 = new Animal(new Vector2d(2, 3), animalConfig);
        Animal animal2 = new Animal(new Vector2d(1, 1), animalConfig);
        Animal animal3 = new Animal(new Vector2d(1, 2), animalConfig);
        Animal animal4 = new Animal(new Vector2d(3, 0), animalConfig);

        map.place(animal1);
        map.place(animal2);
        map.place(animal3);
        map.place(animal4);
        return map;
    }
}
