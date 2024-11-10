package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.RectangularMap;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void isAnimalOrientationCorrect() {
        // given
        Animal animal = new Animal(new Vector2d(2, 2));
        Animal animal2 = new Animal(new Vector2d(3, 3));
        RectangularMap map = new RectangularMap(10, 10);

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
    }

    @Test
    void isAnimalMovementWithinBounds() {
        // given
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(2, 2));
        map.place(animal);

        // when
        animal.move(MoveDirection.FORWARD, map);

        // then
        assertEquals(new Vector2d(2, 3), animal.getPosition());
    }

    @Test
    void isAnimalMovementNotOutOfBounds() {
        // given
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(0, 0));
        map.place(animal);

        Animal animal2 = new Animal(new Vector2d(0, 0));
        map.place(animal2);

        Animal animal3 = new Animal(new Vector2d(5, 5));
        map.place(animal3);

        // when
        animal.move(MoveDirection.BACKWARD, map);
        animal2.move(MoveDirection.LEFT, map);
        animal2.move(MoveDirection.FORWARD, map);
        animal3.move(MoveDirection.FORWARD, map);

        // then
        assertEquals(new Vector2d(0, 0), animal.getPosition());
        assertEquals(new Vector2d(0, 0), animal2.getPosition());
        assertEquals(new Vector2d(5, 5), animal3.getPosition());
    }

    @Test
    void testSimulationRun() {
        // given
        RectangularMap map = new RectangularMap(5, 5);
        List<MoveDirection> directions = OptionsParser.parse(new String[]{"f", "b", "r", "l"});
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));

        // when
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();

        // then
        System.out.println(map); // Visual check for correct simulation
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
}

