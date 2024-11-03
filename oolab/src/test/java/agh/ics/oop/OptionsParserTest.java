package agh.ics.oop;
import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {

    @Test
    void areValidDirectionsHandledCorrectly() {
        // given
        String[] input = {"f", "b", "r", "l"};
        List<MoveDirection> expected = new ArrayList<>();
        expected.add(MoveDirection.FORWARD);
        expected.add(MoveDirection.BACKWARD);
        expected.add(MoveDirection.RIGHT);
        expected.add(MoveDirection.LEFT);

        // when
        List<MoveDirection> output = OptionsParser.parse((input));

        // then
        assertEquals(expected, output);
    }

    @Test
    void areInvalidDirectionsHandledCorrectly() {
        // given
        String[] input = {"f", "n", "b", "x", "r", "y", "l"};
        List<MoveDirection> expected = new ArrayList<>();
        expected.add(MoveDirection.FORWARD);
        expected.add(MoveDirection.BACKWARD);
        expected.add(MoveDirection.RIGHT);
        expected.add(MoveDirection.LEFT);

        List<MoveDirection> output = OptionsParser.parse((input));
        //then
        assertEquals(expected, output);
    }

    @Test
    void areAllInvalidDirectionsHandledCorrectly() {
        // given
        String[] input = {"v", "x", "y", "z"};
        List<MoveDirection> expected = new ArrayList<>();
        // when
        List<MoveDirection> output = OptionsParser.parse((input));

        // then
        assertEquals(expected, output);
    }

    @Test
    void isEmptyInputHandledCorrectly() {
        // given
        String[] input = {};
        List<MoveDirection> expected = new ArrayList<>();
        // when
        List<MoveDirection> output = OptionsParser.parse((input));

        // then
        assertEquals(expected, output);
    }

}