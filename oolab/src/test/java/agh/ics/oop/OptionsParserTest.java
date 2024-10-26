package agh.ics.oop;
import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {

    @Test
    void areValidDirectionsHandledCorrectly() {
        // given
        String[] input = {"f", "b", "r", "l"};
        MoveDirection[] expected = {MoveDirection.FORWARD, MoveDirection.BACKWARD,
                MoveDirection.RIGHT, MoveDirection.LEFT};

        // when
        MoveDirection[] output = OptionsParser.parse((input));

        // then
        assertArrayEquals(expected, output);
    }

    @Test
    void areInvalidDirectionsHandledCorrectly() {
        // given
        String[] input = {"f", "n", "b", "x", "r", "y", "l"};
        MoveDirection[] expected = {MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT, MoveDirection.LEFT};

        MoveDirection[] output = OptionsParser.parse((input));
        //then
        assertArrayEquals(expected, output);
    }

    @Test
    void areAllInvalidDirectionsHandledCorrectly() {
        // given
        String[] input = {"v", "x", "y", "z"};
        MoveDirection[] expected = {};
        // when
        MoveDirection[] output = OptionsParser.parse((input));

        // then
        assertArrayEquals(expected, output);
    }

    @Test
    void isEmptyInputHandledCorrectly() {
        // given
        String[] input = {};
        MoveDirection[] expected = {};
        // when
        MoveDirection[] output = OptionsParser.parse((input));

        // then
        assertArrayEquals(expected, output);
    }

}