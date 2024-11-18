package agh.ics.oop;

import agh.ics.oop.model.*;
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
        // given
        GrassField grassField = new GrassField(10);
        Animal animal = new Animal(new Vector2d(2, 2));
        grassField.place(animal);

        // when
        WorldElement elementAtPosition = grassField.objectAt(new Vector2d(2, 2));

        // then
        assertInstanceOf(Animal.class, elementAtPosition);
    }

    @Test
    void canDoUnlimitedMapMovement() {
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
    }

    @Test
    void visualGrassFieldVerification() {
        // given
        GrassField grassField = new GrassField(20);
        Animal animal = new Animal(new Vector2d(1, 1));
        grassField.place(animal);

        // when
        String mapVisual = grassField.toString();

        // then
        System.out.println(mapVisual);
        assertTrue(mapVisual.contains("^"));
    }
}
