package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void doesToStringGiveProperString() {
        // given
        Vector2d v = new Vector2d(1,2);

        // then
        assertEquals("(1,2)", v.toString());
    }

    @Test
    void doesVectorPrecedeCorrectly() {
        // given
        Vector2d v = new Vector2d(6,9);
        Vector2d v2 = new Vector2d(1,2);
        Vector2d v3 = new Vector2d(1,2);

        // then
        assertTrue(v2.precedes(v));
        assertFalse(v.precedes(v2));
        assertTrue(v2.precedes(v3));
    }

    @Test
    void doesVectorFollowCorrectly() {
        // given
        Vector2d v = new Vector2d(6,9);
        Vector2d v2 = new Vector2d(1,2);
        Vector2d v3 = new Vector2d(1,2);

        // then
        assertFalse(v2.follows(v));
        assertTrue(v.follows(v2));
        assertTrue(v2.follows(v3));
    }

    @Test
    void doVectorsAdd() {
        // given
        Vector2d v = new Vector2d(6,9);
        Vector2d v2 = new Vector2d(1,2);
        Vector2d v3 = new Vector2d(-10,-2);

        // when
        Vector2d v4 = v.add(v2);
        Vector2d v5 = v.add(v3);

        // then
        assertEquals(7, v4.getX());
        assertEquals(11, v4.getY());

        assertEquals(-4, v5.getX());
        assertEquals(7, v5.getY());
    }

    @Test
    void doVectorsSubtract() {
        Vector2d v = new Vector2d(6,9);
        Vector2d v2 = new Vector2d(1,2);
        Vector2d v3 = new Vector2d(-10,-2);

        // when
        Vector2d v4 = v.subtract(v2);
        Vector2d v5 = v.subtract(v3);

        // then
        assertEquals(5, v4.getX());
        assertEquals(7, v4.getY());

        assertEquals(16, v5.getX());
        assertEquals(11, v5.getY());
    }

    @Test
    void doesUpperRightGiveCorrectValues() {
        // given
        Vector2d v = new Vector2d(65,96);
        Vector2d v2 = new Vector2d(17,128);
        Vector2d v3 = new Vector2d(99,100);
        Vector2d v4 = new Vector2d(65,96);

        // when
        Vector2d v5 = v.upperRight(v2);
        Vector2d v6 = v.upperRight(v3);
        Vector2d v7 = v.upperRight(v4);

        // then
        assertEquals(65,v5.getX());
        assertEquals(128,v5.getY());
        assertEquals(99,v6.getX());
        assertEquals(100,v6.getY());
        assertEquals(65,v7.getX());
        assertEquals(96,v7.getY());
    }

    @Test
    void doesLowerLeftGiveCorrectValues() {
        // given
        Vector2d v = new Vector2d(65,96);
        Vector2d v2 = new Vector2d(17,128);
        Vector2d v3 = new Vector2d(99,100);
        Vector2d v4 = new Vector2d(65,96);

        // when
        Vector2d v5 = v.lowerLeft(v2);
        Vector2d v6 = v.lowerLeft(v3);
        Vector2d v7 = v.lowerLeft(v4);

        // then
        assertEquals(17,v5.getX());
        assertEquals(96,v5.getY());
        assertEquals(65,v6.getX());
        assertEquals(96,v6.getY());
        assertEquals(65,v7.getX());
        assertEquals(96,v7.getY());
    }

    @Test
    void doesOppositeGiveOppositeVectors() {
        // given
        Vector2d v = new Vector2d(65,96);
        Vector2d v2 = new Vector2d(-17,128);
        Vector2d v3 = new Vector2d(-99,-100);

        // when
        Vector2d v4 = v.opposite(v);
        Vector2d v5 = v.opposite(v2);
        Vector2d v6 = v.opposite(v3);

        // then
        assertEquals(-65, v4.getX());
        assertEquals(-96, v4.getY());
        assertEquals(17,v5.getX());
        assertEquals(-128,v5.getY());
        assertEquals(99 ,v6.getX());
        assertEquals(100,v6.getY());
    }

    @Test
    void doesEqualCorrectlyTellIfEqual() {
        // given
        Vector2d v = new Vector2d(65,96);
        Vector2d v2 = new Vector2d(-17,128);
        Vector2d v3 = new Vector2d(65,96);


        // then
        assertFalse(v.equals(v2));
        assertTrue(v.equals(v3));
        assertTrue(v3.equals(v));


    }
}