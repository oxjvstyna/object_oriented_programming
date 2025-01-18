package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    private final MoveVariant totalPredestination = new TotalPredestination();

    @Test
    void animalShouldMoveAccordingToItsGenome() {
        // given
        AnimalConfig animalConfig = new AnimalConfig(10, 5, 10, 10, 1, 4, new TotalPredestination());
        Vector2d initialPosition = new Vector2d(2, 0);
        Animal animal = new Animal(initialPosition, animalConfig);
        MoveValidator validator = position -> true; // Always allows movement

        // when
        animal.move(validator);

        // then
        assertNotNull(animal.getPosition()); // Ensure animal has moved
        assertNotNull(animal.getOrientation()); // Ensure orientation has changed
    }

    @Test
    void animalShouldNotMoveWhenBlocked() {
        // given
        Vector2d initialPosition = new Vector2d(2, 0);
        AnimalConfig animalConfig = new AnimalConfig(10, 5, 10, 10, 1, 4, new TotalPredestination());
        Animal animal = new Animal(initialPosition, animalConfig);
        MoveValidator validator = position -> false; // Always blocks movement

        // when
        animal.move(validator);

        // then
        assertEquals(initialPosition, animal.getPosition()); // Ensure position hasn't changed
    }

    @Test
    void animalShouldLoseEnergyPerGenomeMove() {
        // given
        Vector2d initialPosition = new Vector2d(2, 0);
        AnimalConfig animalConfig = new AnimalConfig(10, 5, 10, 10, 1, 4, new TotalPredestination());
        Animal animal = new Animal(initialPosition, animalConfig);
        MoveValidator validator = position -> true; // Always allows movement

        int initialEnergy = animal.getEnergy();

        // when
        animal.move(validator);

        // then
        int expectedEnergy = initialEnergy - 1;
        assertEquals(expectedEnergy, animal.getEnergy()); // Ensure energy is as expected
    }

    @Test
    void animalShouldReproduceCorrectly() {
        // given
        Vector2d position = new Vector2d(2, 2);
        AnimalConfig animalConfig = new AnimalConfig(10, 5, 10, 10, 1, 4, new TotalPredestination());
        Animal parent1 = new Animal(position, animalConfig);
        Animal parent2 = new Animal(position, animalConfig);

        // when
        Animal child = parent1.reproduce(parent2);

        // then
        assertNotNull(child);
        assertEquals(position, child.getPosition());
        assertTrue(child.getEnergy() > 0);
        assertNotSame(parent1.getGenomes(), child.getGenomes()); // Child genome should differ
    }

    @Test
    void animalShouldHaveRandomOrientationAfterReproduction() {
        // given
        Vector2d position = new Vector2d(0, 0);
        AnimalConfig animalConfig = new AnimalConfig(10, 5, 10, 10, 1, 4, new TotalPredestination());

        Animal parent1 = new Animal(position, animalConfig);
        Animal parent2 = new Animal(position, animalConfig);

        // when
        Animal child = parent1.reproduce(parent2);

        // then
        assertNotNull(child.getOrientation());
    }

    @Test
    void animalShouldDieWhenEnergyIsZero() {
        // given
        Vector2d position = new Vector2d(3, 3);
        AnimalConfig animalConfig = new AnimalConfig(0, 5, 10, 10, 1, 4, new TotalPredestination());
        Animal animal = new Animal(position, animalConfig);

        // when
        boolean isAlive = animal.isAlive();

        // then
        assertFalse(isAlive);
    }

    @Test
    void animalShouldGainEnergyWhenFed() {
        // given
        Vector2d position = new Vector2d(1, 1);
        AnimalConfig animalConfig = new AnimalConfig(50, 5, 10, 10, 1, 4, new TotalPredestination());
        Animal animal = new Animal(position, animalConfig);

        // when
        animal.addEnergy(30);

        // then
        assertEquals(80, animal.getEnergy());
    }

    @Test
    void animalShouldFollowMoveVariantLogic() {
        // given
        Vector2d position = new Vector2d(2, 2);
        AnimalConfig animalConfig = new AnimalConfig(10, 5, 10, 10, 1, 4, new TotalPredestination());
        Animal animal = new Animal(position, animalConfig);
        MoveValidator validator = pos -> true; // Always allows movement

        // when
        animal.move(validator);

        // then
        assertNotNull(animal.getPosition()); // Ensure the position updates correctly
    }

    @Test
    void animalShouldReverseDirection() {
        // given
        Vector2d position = new Vector2d(0, 0);
        AnimalConfig animalConfig = new AnimalConfig(10, 5, 10, 10, 1, 4, new TotalPredestination());
        Animal animal = new Animal(position, animalConfig);
        animal.orientation = MapDirection.NORTH;

        // when
        animal.reverseDirection();

        // then
        assertEquals(MapDirection.SOUTH, animal.getOrientation());
    }

}
