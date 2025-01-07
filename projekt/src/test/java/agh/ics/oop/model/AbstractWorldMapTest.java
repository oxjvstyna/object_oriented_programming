package agh.ics.oop.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AbstractWorldMapTest {

    private AbstractWorldMap worldMap;

    @BeforeEach
    void setUp() {
        worldMap = new EarthMap(10, 10, new FertileEquator(10, 10), new TotalPredestination());
    }

    @Test
    void shouldRemoveDeadAnimals() {
        // given
        Animal aliveAnimal = new Animal(new Vector2d(1, 1), 10, 5, 10, 10, 1, 4, new TotalPredestination());
        Animal deadAnimal = new Animal(new Vector2d(2, 2), 0, 5, 10, 10, 1, 4, new TotalPredestination());

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
        Animal animal = new Animal(new Vector2d(1, 1), 10, 5, 10, 10, 1, 4, new TotalPredestination());
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

        Animal animal = new Animal(plantPosition, 10, 5, 10, 10, 1, 4, new TotalPredestination());
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
        Animal parent1 = new Animal(new Vector2d(3, 3), 20, 5, 10, 10, 1, 4, new TotalPredestination());
        Animal parent2 = new Animal(new Vector2d(3, 3), 20, 5, 10, 10, 1, 4, new TotalPredestination());
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

    private static AbstractWorldMap getAbstractWorldMap(GrowthVariant growthVariant) {
        MoveVariant moveVariant = new TotalPredestination();
        AbstractWorldMap map = new EarthMap(10, 10, growthVariant, moveVariant);

        // when
        Animal animal1 = new Animal(new Vector2d(2, 3), 10, 5, 10, 10, 1, 4, null);
        Animal animal2 = new Animal(new Vector2d(1, 1), 10, 5, 10, 10, 1, 4, null);
        Animal animal3 = new Animal(new Vector2d(1, 2), 10, 5, 10, 10, 1, 4, null);
        Animal animal4 = new Animal(new Vector2d(3, 0), 10, 5, 10, 10, 1, 4, null);

        map.place(animal1);
        map.place(animal2);
        map.place(animal3);
        map.place(animal4);
        return map;
    }
}
