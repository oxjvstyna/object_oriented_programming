package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void isAnimalOrientationCorrect() {
        // given
        Animal animal = new Animal();
        Animal animal2 = new Animal();

        // when
        animal.move(MoveDirection.RIGHT);
        animal2.move(MoveDirection.LEFT);

        // then
        assertEquals(MapDirection.EAST, animal.getOrientation());
        assertEquals(MapDirection.WEST, animal2.getOrientation());

        // when
        animal.move(MoveDirection.LEFT);
        animal2.move(MoveDirection.RIGHT);

        // then
        assertEquals(MapDirection.NORTH, animal.getOrientation());
        assertEquals(MapDirection.NORTH, animal2.getOrientation());
    }

    @Test
    void isAnimalMovementWithinBounds() {
        // given
        Animal animal = new Animal();

        // when
        animal.move(MoveDirection.FORWARD);

        // then
        assertEquals(new Vector2d(2, 3), animal.getPosition());
    }

    @Test
    void isAnimalMovementNotOutOfBounds() {
        // given
        Animal animal = new Animal(new Vector2d(0, 0));
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.RIGHT);
        Animal animal2 = new Animal(new Vector2d(0, 0));
        animal2.move(MoveDirection.BACKWARD);
        Animal animal3 = new Animal(new Vector2d(4, 4));
        animal3.move(MoveDirection.FORWARD);

        // when
        animal.move(MoveDirection.FORWARD);

        // then
        assertEquals(new Vector2d(0, 0), animal.getPosition());
        assertEquals(new Vector2d(0, 0), animal2.getPosition());
        assertEquals(new Vector2d(4, 4), animal3.getPosition());
    }

    @Test
    void testSimulationRun() {
        // given
        List<MoveDirection> directions = OptionsParser.parse(new String[]{"f", "b", "r", "l"});
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));

        // when
        Simulation simulation = new Simulation(positions, directions);
        simulation.run();

        // then
        // Wizualne sprawdzenie, czy program wypisuje na ekran poprawne wyniki
    }


    @Test
    void isLongMovementSequenceCorrect() {
        // given
        Animal animal = new Animal();

        // when
        MoveDirection[] moves = {MoveDirection.FORWARD, MoveDirection.RIGHT, MoveDirection.FORWARD,
                MoveDirection.RIGHT, MoveDirection.FORWARD, MoveDirection.RIGHT,
                MoveDirection.FORWARD, MoveDirection.RIGHT};
        for (MoveDirection move : moves) {
            animal.move(move);
        }

        // then
        assertEquals(new Vector2d(2, 2), animal.getPosition());
        assertEquals(MapDirection.NORTH, animal.getOrientation());
    }
    @Test
    void doesSimulationWithNoDirectionsRunCorrectly() {
        // given
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
        List<MoveDirection> directions = List.of();

        // when
        Simulation simulation = new Simulation(positions, directions);
        simulation.run();

        // then
        assertEquals(new Vector2d(2, 2), simulation.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(3, 4), simulation.getAnimals().get(1).getPosition());
    }

    @Test
    void areAnimalAlternatingMovementsCorrect() {
        // given
        List<MoveDirection> directions = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "f", "l", "f", "b"});
        List<Vector2d> positions = List.of(new Vector2d(1, 1), new Vector2d(3, 3), new Vector2d(2, 2));

        // when
        Simulation simulation = new Simulation(positions, directions);
        simulation.run();

        // then
        Animal animal1 = simulation.getAnimals().get(0);
        Animal animal2 = simulation.getAnimals().get(1);
        Animal animal3 = simulation.getAnimals().get(2);

        assertEquals(new Vector2d(1, 3), animal1.getPosition());
        assertEquals(MapDirection.NORTH, animal1.getOrientation());

        assertEquals(new Vector2d(3, 3), animal2.getPosition());
        assertEquals(MapDirection.NORTH, animal2.getOrientation());

        assertEquals(new Vector2d(3, 2), animal3.getPosition());
        assertEquals(MapDirection.NORTH, animal3.getOrientation());
    }
}
