package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void isAnimalOrientationCorrect() {
        try{
            // given
            Animal animal = new Animal(new Vector2d(2, 2));
            Animal animal2 = new Animal(new Vector2d(3, 3));
            RectangularMap map = new RectangularMap(10, 10);
            map.place(animal);
            map.place(animal2);

            // when
            animal.move(MoveDirection.RIGHT, map);
            animal2.move(MoveDirection.LEFT, map);

            // then
            assertEquals(MapDirection.EAST, animal.getOrientation());
            assertEquals(MapDirection.WEST, animal2.getOrientation());

            // when
            animal.move(MoveDirection.LEFT, map);
            animal2.move(MoveDirection.RIGHT, map);

            // then
            assertEquals(MapDirection.NORTH, animal.getOrientation());
            assertEquals(MapDirection.NORTH, animal2.getOrientation());
        } catch (IncorrectPositionException e) {
            fail("Niespodziewany IncorrectPositionException: " + e.getMessage());
        }

    }

    @Test
    void isAnimalMovementWithinBounds() {
        try {
            // given
            RectangularMap map = new RectangularMap(5, 5);
            Animal animal = new Animal(new Vector2d(2, 2));
            map.place(animal);

            // when
            animal.move(MoveDirection.FORWARD, map);

            // then
            assertEquals(new Vector2d(2, 3), animal.getPosition());
        } catch (IncorrectPositionException e) {
            fail("Niespodziewany IncorrectPositionException: " + e.getMessage());
        }
    }


    @Test
    void visualSimulationCheck() {
        // given
        WorldMap<Animal, Vector2d> map = new RectangularMap(5, 5);
        List<MoveDirection> directions = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f"});
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));

        // when
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();

        // then
        System.out.println(map);
    }
    

    @Test
    void doesSimulationWithNoDirectionsRunCorrectly() {
        // given
        RectangularMap map = new RectangularMap(5, 5);
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
        List<MoveDirection> directions = List.of();

        // when
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();

        // then
        List<Animal> animals = new ArrayList<>(map.getAnimals().values());
        assertEquals(new Vector2d(2, 2), animals.get(0).getPosition());
        assertEquals(new Vector2d(3, 4), animals.get(1).getPosition());
    }

    @Test
    void areAlternatingMovementsCorrect() {
        // given
        RectangularMap map = new RectangularMap(5, 5);
        List<MoveDirection> directions = OptionsParser.parse(new String[]{"f", "r", "f", "l", "b", "f", "r", "f", "l", "b"});
        List<Vector2d> positions = List.of(new Vector2d(1, 1), new Vector2d(3, 3));

        // when
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();

        // then
        List<Animal> animals = new ArrayList<>(map.getAnimals().values());

        Animal animal1 = animals.get(0);
        Animal animal2 = animals.get(1);

        assertEquals(new Vector2d(1, 2), animal1.getPosition());
        assertEquals(MapDirection.NORTH, animal1.getOrientation());

        assertEquals(new Vector2d(3, 4), animal2.getPosition());
        assertEquals(MapDirection.NORTH, animal2.getOrientation());
    }

    @Test
    void doesAnimalPlacementNotCollide() throws IncorrectPositionException {
            // given
            RectangularMap map = new RectangularMap(5, 5);
            Animal animal1 = new Animal(new Vector2d(2, 2));
            Animal animal2 = new Animal(new Vector2d(2, 2)); // ten sam początkowy punkt

            // when
            map.place(animal1);

            // then
            assertThrows(
                    IncorrectPositionException.class,
                    () -> map.place(animal2)
            );
    }

    @Test
    void doesAnimalFullyRotate() {
        // given
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(2, 2));

        // when
        animal.move(MoveDirection.RIGHT, map);
        animal.move(MoveDirection.RIGHT, map);
        animal.move(MoveDirection.RIGHT, map);
        animal.move(MoveDirection.RIGHT, map);

        // then
        assertEquals(MapDirection.NORTH, animal.getOrientation());
    }

    @Test
    void doAnimalsNotCollideDuringMovement() {
        try{
            // given
            RectangularMap map = new RectangularMap(5, 5);
            Animal animal1 = new Animal(new Vector2d(2, 2));
            Animal animal2 = new Animal(new Vector2d(2, 3));
            map.place(animal1);
            map.place(animal2);

            // when
            animal1.move(MoveDirection.FORWARD, map);

            // then
            assertEquals(new Vector2d(2, 2), animal1.getPosition());
            assertEquals(new Vector2d(2, 3), animal2.getPosition());
        }catch (IncorrectPositionException e) {
            fail("Niespodziewany IncorrectPositionException: " + e.getMessage());
        }

    }

    @Test
    void doAnimalsMoveCorrectlyWithinLongSequence() {
        try{
            // given
            RectangularMap map = new RectangularMap(10, 10);
            Animal animal = new Animal(new Vector2d(5, 5));
            map.place(animal);

            // when
            MoveDirection[] moves = {
                    MoveDirection.FORWARD, MoveDirection.RIGHT,
                    MoveDirection.FORWARD, MoveDirection.LEFT,
                    MoveDirection.FORWARD, MoveDirection.FORWARD,
                    MoveDirection.RIGHT, MoveDirection.BACKWARD,
                    MoveDirection.LEFT, MoveDirection.FORWARD
            };

            for (MoveDirection move : moves) {
                animal.move(move, map);
            }

            // then
            assertEquals(new Vector2d(5, 9), animal.getPosition());
            assertEquals(MapDirection.NORTH, animal.getOrientation());
        }catch (IncorrectPositionException e) {
            fail("Niespodziewany IncorrectPositionException: " + e.getMessage());
        }
    }

    @Test
    void canAnimalsNotBePlacedOutOfBounds(){
        // given
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(new Vector2d(6, 6));
        Animal animal2 = new Animal(new Vector2d(-2, 4));
        Animal animal3 = new Animal(new Vector2d(-2, -10));
        Animal animal4 = new Animal(new Vector2d(3, 4));

        // when & then
        assertThrows(
                IncorrectPositionException.class,
                () -> map.place(animal1)
        );
        assertThrows(
                IncorrectPositionException.class,
                () -> map.place(animal2)
        );
        assertThrows(
                IncorrectPositionException.class,
                () -> map.place(animal3)
        );
        assertDoesNotThrow(
                () -> map.place(animal4));
    }
}



