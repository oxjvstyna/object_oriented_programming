package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void animalShouldMoveCorrectly() {
        // given
        Vector2d initialPosition = new Vector2d(2, 2);
        Animal animal = new Animal(initialPosition, 100, 5, 50, 20, 1, 3);
        MoveValidator validator = position -> true; // Always allows movement

        // when
        animal.move(MoveDirection.FORWARD, validator);
        animal.move(MoveDirection.RIGHT, validator);
        animal.move(MoveDirection.FORWARD, validator);

        // then
        assertEquals(new Vector2d(3, 3), animal.getPosition());
        assertEquals(MapDirection.EAST, animal.getOrientation());
    }

    @Test
    void animalShouldNotMoveWhenBlocked() {
        // given
        Vector2d initialPosition = new Vector2d(2, 2);
        Animal animal = new Animal(initialPosition, 100, 5, 50, 20, 1, 3);
        MoveValidator validator = position -> false; // Blocks movement

        // when
        animal.move(MoveDirection.FORWARD, validator);

        // then
        assertEquals(initialPosition, animal.getPosition());
    }

    @Test
    void animalShouldLoseEnergyCorrectly() {
        // given
        Vector2d initialPosition = new Vector2d(2, 2);
        Animal animal = new Animal(initialPosition, 100, 5, 50, 20, 1, 3);

        // when
        animal.changeEnergy(-30);

        // then
        assertEquals(70, animal.getEnergy());
    }

    @Test
    void animalShouldNotHaveNegativeEnergy() {
        // given
        Vector2d initialPosition = new Vector2d(2, 2);
        Animal animal = new Animal(initialPosition, 50, 5, 50, 20, 1, 3);

        // when
        animal.changeEnergy(-60);

        // then
        assertEquals(0, animal.getEnergy());
    }

    @Test
    void animalShouldReproduceCorrectly() {
        // given
        Vector2d position = new Vector2d(3, 3);
        Genome parent1Genome = new Genome(5);
        Genome parent2Genome = new Genome(5);
        Animal parent1 = new Animal(position, 100, parent1Genome, null, null, 50, 20, 1, 3);
        Animal parent2 = new Animal(position, 100, parent2Genome, null, null, 50, 20, 1, 3);

        // when
        Animal child = parent1.reproduce(parent1, parent2);

        // then
        assertNotNull(child);
        assertEquals(position, child.getPosition());
        assertEquals(40, parent1.getEnergy()); // 100 - birthEnergy
        assertEquals(40, parent2.getEnergy()); // 100 - birthEnergy
        assertTrue(child.getEnergy() > 0);
        assertNotNull(child.getGenomes());
    }

    @Test
    void animalShouldChangeOrientationCorrectly() {
        // given
        Vector2d initialPosition = new Vector2d(2, 2);
        Animal animal = new Animal(initialPosition, 100, 5, 50, 20, 1, 3);

        // when
        animal.move(MoveDirection.RIGHT, position -> true);
        animal.move(MoveDirection.BACKWARD_LEFT, position -> true);

        // then
        assertEquals(MapDirection.NORTH_WEST, animal.getOrientation());
    }

    @Test
    void animalShouldReverseDirectionCorrectly() {
        // given
        Animal animal = new Animal(new Vector2d(0, 0), 100, 5, 50, 20, 1, 3);
        animal.orientation = MapDirection.NORTH;

        // when
        animal.reverseDirection();

        // then
        assertEquals(MapDirection.SOUTH, animal.getOrientation());
    }

    @Test
    void animalShouldBeAliveWhenEnergyIsPositive() {
        // given
        Animal animal = new Animal(new Vector2d(0, 0), 10, 5, 50, 20, 1, 3);

        // when
        boolean isAlive = animal.isAlive();

        // then
        assertTrue(isAlive);
    }

    @Test
    void animalShouldBeDeadWhenEnergyIsZero() {
        // given
        Animal animal = new Animal(new Vector2d(0, 0), 0, 5, 50, 20, 1, 3);

        // when
        boolean isAlive = animal.isAlive();

        // then
        assertFalse(isAlive);
    }
}
