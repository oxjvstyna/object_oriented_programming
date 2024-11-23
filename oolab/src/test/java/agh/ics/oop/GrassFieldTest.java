package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {

    @Test
    void isGrassCountCorrect() {
        // given
        GrassField grassField = new GrassField(10);

        // when
        int grassCount = grassField.getGrasses().size();

        // then
        assertEquals(10, grassCount);
    }

    @Test
    void isAnimalPrioritizedOverGrass() {
        try {
            // given
            GrassField grassField = new GrassField(10);
            Animal animal = new Animal(new Vector2d(2, 2));
            grassField.place(animal);

            // when
            WorldElement elementAtPosition = grassField.objectAt(new Vector2d(2, 2));

            // then
            assertInstanceOf(Animal.class, elementAtPosition);
        } catch (IncorrectPositionException e) {
            fail("Niespodziewany IncorrectPositionException: " + e.getMessage());
        }
    }

    @Test
    void canDoUnlimitedMapMovement() {
        try {
            // given
            GrassField grassField = new GrassField(10);
            Animal animal = new Animal(new Vector2d(0, 0));
            grassField.place(animal);

            // when
            animal.move(MoveDirection.FORWARD, grassField);
            animal.move(MoveDirection.FORWARD, grassField);
            animal.move(MoveDirection.FORWARD, grassField);
            animal.move(MoveDirection.FORWARD, grassField);
            animal.move(MoveDirection.FORWARD, grassField);
            animal.move(MoveDirection.FORWARD, grassField);

            // then
            assertEquals(new Vector2d(0, 6), animal.getPosition());
        } catch (IncorrectPositionException e) {
            fail("Niespodziewany IncorrectPositionException: " + e.getMessage());
        }
    }

    @Test
    void visualGrassFieldVerification() {
        try {
            // given
            GrassField grassField = new GrassField(20);
            Animal animal = new Animal(new Vector2d(1, 1));
            grassField.place(animal);

            // when
            String mapVisual = grassField.toString();

            // then
            System.out.println(mapVisual);
            assertTrue(mapVisual.contains("^"));
        } catch (IncorrectPositionException e) {
            fail("Niespodziewany IncorrectPositionException: " + e.getMessage());
        }
    }

    @Test
    void doesAnimalPlacementThrowForOccupiedPosition() {
        try {
            // given
            GrassField grassField = new GrassField(10);
            Animal animal1 = new Animal(new Vector2d(2, 2));
            grassField.place(animal1);

            // when & then
            Animal animal2 = new Animal(new Vector2d(2, 2));
            assertThrows(
                    IncorrectPositionException.class,
                    () -> grassField.place(animal2)
            );
        } catch (IncorrectPositionException e) {
            fail("Niespodziewany IncorrectPositionException: " + e.getMessage());
        }
    }

    @Test
    void isOccupiedReturnsCorrectValues() {
        try {
            // given
            GrassField grassField = new GrassField(10);
            Animal animal = new Animal(new Vector2d(2, 2));
            grassField.place(animal);

            // when
            boolean isOccupied1 = grassField.isOccupied(new Vector2d(2, 2));
            boolean isOccupied2 = grassField.isOccupied(new Vector2d(3, 3));

            // then
            assertTrue(isOccupied1);
            assertFalse(isOccupied2);
        } catch (IncorrectPositionException e) {
            fail("Niespodziewany IncorrectPositionException: " + e.getMessage());
        }
    }

    @Test
    void canMoveToDetectsObstacles() {
        try {
            // given
            GrassField grassField = new GrassField(10);
            Animal animal = new Animal(new Vector2d(2, 2));
            grassField.place(animal);

            // when
            boolean canMove1 = grassField.canMoveTo(new Vector2d(3, 3));
            boolean canMove2 = grassField.canMoveTo(new Vector2d(2, 2));

            // then
            assertTrue(canMove1);
            assertFalse(canMove2 );
        } catch (IncorrectPositionException e) {
            fail("Niespodziewany IncorrectPositionException: " + e.getMessage());
        }
    }

    @Test
    void doesMoveUpdateAnimalPosition() {
        try {
            // given
            GrassField grassField = new GrassField(10);
            Animal animal = new Animal(new Vector2d(2, 2));
            grassField.place(animal);

            // when
            grassField.move(animal, MoveDirection.FORWARD);

            // then
            assertEquals(new Vector2d(2, 3), animal.getPosition());
        } catch (IncorrectPositionException e) {
            fail("Niespodziewany IncorrectPositionException: " + e.getMessage());
        }
    }

    @Test
    void doesObjectAtReturnCorrectElement() {
        try {
            // given
            GrassField grassField = new GrassField(10);
            Animal animal = new Animal(new Vector2d(2, 2));
            grassField.place(animal);

            // when
            WorldElement element = grassField.objectAt(new Vector2d(2, 2));

            // then
            assertEquals(animal, element);
        } catch (IncorrectPositionException e) {
            fail("Niespodziewany IncorrectPositionException: " + e.getMessage());
        }
    }

    @Test
    void doesGetElementsCountAllWorldElements() {
        try {
            // given
            GrassField grassField = new GrassField(10);
            Animal animal = new Animal(new Vector2d(2, 2));
            grassField.place(animal);

            // when
            int totalElements = grassField.getElements().size();

            // then
            assertEquals(11, totalElements);
        } catch (IncorrectPositionException e) {
            fail("Niespodziewany IncorrectPositionException: " + e.getMessage());
        }
    }
}
