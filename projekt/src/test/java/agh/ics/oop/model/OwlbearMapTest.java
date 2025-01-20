package agh.ics.oop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OwlbearMapTest {
    private OwlbearMap map;
    private final int mapWidth = 100;
    private final int mapHeight = 100;

    @BeforeEach
    void setup() {
        GrowthVariant growthVariant = new FertileEquator(mapWidth, mapHeight);
        MoveVariant moveVariant = new TotalPredestination();
        AnimalConfig config = new AnimalConfig(100, 100, 100, 100, 100, 100, moveVariant);
        map = new OwlbearMap(mapWidth, mapHeight, growthVariant, config);
    }

    @Test
    void testTerritoryGeneration() {
        // given
        int sideLength = (int) Math.sqrt(0.2 * mapWidth * mapHeight);

        // when
        Vector2d lowerLeft = map.getTerritoryLowerLeft();
        Vector2d upperRight = map.getTerritoryUpperRight();

        // then
        // Validate territory size
        assertEquals(sideLength, upperRight.x() - lowerLeft.x() + 1);
        assertEquals(sideLength, upperRight.y() - lowerLeft.y() + 1);

        // then
        // Validate territory boundaries
        assertTrue(lowerLeft.x() >= 0 && lowerLeft.y() >= 0);
        assertTrue(upperRight.x() < mapWidth && upperRight.y() < mapHeight);
    }

    @Test
    void testOwlbearMovementWithinTerritory() {
        // given
        Vector2d lowerLeft = map.getTerritoryLowerLeft();
        Vector2d upperRight = map.getTerritoryUpperRight();

        // when
        for (int i = 0; i < 100; i++) {
            map.owlbear.move(position -> position.follows(lowerLeft) && position.precedes(upperRight));
            Vector2d position = map.owlbear.getPosition();

            // then
            // Ensure the Owlbear stays within its territory
            assertTrue(position.follows(lowerLeft));
            assertTrue(position.precedes(upperRight));
        }
    }


    @Test
    void testOwlbearEatsAnimals() {
        // given
        Vector2d owlbearPosition = map.owlbear.getPosition();
        AnimalConfig config = new AnimalConfig(50, 5, 5, 5, 5, 1, new TotalPredestination());
        Animal animal = new Animal(owlbearPosition, config);
        map.place(animal);

        // when
        map.eatAnimals();

        // then
        // Check that the animal's energy has been reduced to zero
        assertTrue(animal.getEnergy() <= 0);
    }

}
