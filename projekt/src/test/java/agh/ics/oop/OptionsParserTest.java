package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class OptionsParserTest {

    @Test
    void testParseCorrectArguments() {
        // Test z poprawnymi argumentami
        String[] args = {"0", "1", "2", "3", "4", "5", "6", "7"};
        List<MoveDirection> directions = OptionsParser.parse(args);

        assertEquals(8, directions.size());
        assertEquals(MoveDirection.FORWARD, directions.get(0));
        assertEquals(MoveDirection.FORWARD_RIGHT, directions.get(1));
        assertEquals(MoveDirection.RIGHT, directions.get(2));
        assertEquals(MoveDirection.BACKWARD_RIGHT, directions.get(3));
        assertEquals(MoveDirection.BACKWARD, directions.get(4));
        assertEquals(MoveDirection.BACKWARD_LEFT, directions.get(5));
        assertEquals(MoveDirection.LEFT, directions.get(6));
        assertEquals(MoveDirection.FORWARD_LEFT, directions.get(7));
    }

    @Test
    void testParseEmptyArguments() {
        // Test z pustymi argumentami
        String[] args = {};
        List<MoveDirection> directions = OptionsParser.parse(args);

        assertTrue(directions.isEmpty(), "Lista powinna być pusta.");
    }

    @Test
    void testParseInvalidArgument() {
        // Test z niepoprawnym argumentem
        String[] args = {"8"};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            OptionsParser.parse(args);
        });

        assertEquals("8 jest nieprawidlowym ruchem.", exception.getMessage());
    }

    @Test
    void testParseMixedValidAndInvalidArguments() {
        // Test z mieszanką poprawnych i niepoprawnych argumentów
        String[] args = {"0", "1", "8"};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            OptionsParser.parse(args);
        });

        assertEquals("8 jest nieprawidlowym ruchem.", exception.getMessage());
    }
}
