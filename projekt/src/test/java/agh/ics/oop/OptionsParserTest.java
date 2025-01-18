package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class OptionsParserTest {

    @Test
    void shouldParseValidDirections() {
        // given
        String[] input = {"0", "1", "2", "3", "4", "5", "6", "7"};

        // when
        List<MoveDirection> result = OptionsParser.parse(input);

        // then
        assertEquals(List.of(
                MoveDirection.FORWARD,
                MoveDirection.FORWARD_RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.BACKWARD_RIGHT,
                MoveDirection.BACKWARD,
                MoveDirection.BACKWARD_LEFT,
                MoveDirection.LEFT,
                MoveDirection.FORWARD_LEFT
        ), result);
    }

    @Test
    void shouldThrowExceptionForInvalidDirection() {
        // given
        String[] input = {"0", "1", "x", "4"};

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> OptionsParser.parse(input));
        assertEquals("x jest nieprawidlowym ruchem.", exception.getMessage());
    }

    @Test
    void shouldHandleEmptyInput() {
        // given
        String[] input = {};

        // when
        List<MoveDirection> result = OptionsParser.parse(input);

        // then
        assertTrue(result.isEmpty(), "Expected an empty list for empty input.");
    }

    @Test
    void shouldThrowExceptionForPartiallyInvalidInput() {
        // given
        String[] input = {"0", "9", "1"};

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> OptionsParser.parse(input));
        assertEquals("9 jest nieprawidlowym ruchem.", exception.getMessage());
    }

    @Test
    void shouldIgnoreWhitespaceInInput() {
        // given
        String[] input = {" 0 ", "1", " 2"};

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> OptionsParser.parse(input));
        assertEquals(" 0  jest nieprawidlowym ruchem.", exception.getMessage());
    }
}
