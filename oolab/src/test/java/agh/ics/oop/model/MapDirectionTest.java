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
    @Test
    void isToUnitVectorCorrect(){
        // given
        MapDirection north = MapDirection.NORTH;
        MapDirection south = MapDirection.SOUTH;
        MapDirection west = MapDirection.WEST;
        MapDirection east = MapDirection.EAST;

        // when
        Vector2d v1 = north.toUnitVector();
        Vector2d v2 = south.toUnitVector();
        Vector2d v3 = west.toUnitVector();
        Vector2d v4 = east.toUnitVector();

        // then
        assertEquals(0, v1.getX());
        assertEquals(1, v1.getY());
        assertEquals(0, v2.getX());
        assertEquals(-1, v2.getY());
        assertEquals(-1, v3.getX());
        assertEquals(0, v3.getY());
        assertEquals(1, v4.getX());
        assertEquals(0, v4.getY());
    }
}
