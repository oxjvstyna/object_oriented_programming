package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {

    @Test
    void nextDirectionIsCorrect() {
        // given
        MapDirection north = MapDirection.NORTH;
        MapDirection south = MapDirection.SOUTH;
        MapDirection west = MapDirection.WEST;
        MapDirection east = MapDirection.EAST;

        // then
        assertEquals(MapDirection.EAST, north.next());
        assertEquals(MapDirection.SOUTH, east.next());
        assertEquals(MapDirection.WEST, south.next());
        assertEquals(MapDirection.NORTH, west.next());
    }

    @Test
    void previousDirectionIsCorrect() {
        // given
        MapDirection north = MapDirection.NORTH;
        MapDirection south = MapDirection.SOUTH;
        MapDirection west = MapDirection.WEST;
        MapDirection east = MapDirection.EAST;

        // then
        assertEquals(MapDirection.NORTH, east.previous());
        assertEquals(MapDirection.SOUTH, west.previous());
        assertEquals(MapDirection.EAST, south.previous());
        assertEquals(MapDirection.WEST, north.previous());
    }
}
