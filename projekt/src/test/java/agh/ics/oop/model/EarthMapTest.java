package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EarthMapTest {

    @Test
    void animalShouldWrapAroundHorizontallyWhenExitingLeft() {
        EarthMap map = new EarthMap(10, 5, new FertileEquator(10, 5), new TotalPredestination());
        Animal animal = new Animal(new Vector2d(-1, 2), 100, 5, 50, 20, 1, 3, new TotalPredestination());
        map.handleBorder(animal);
        assertEquals(new Vector2d(9, 2), animal.getPosition());
    }

    @Test
    void animalShouldWrapAroundHorizontallyWhenExitingRight() {
        EarthMap map = new EarthMap(10, 5, new FertileEquator(10, 5), new TotalPredestination());
        Animal animal = new Animal(new Vector2d(11, 2), 100, 5, 50, 20, 1, 3, new TotalPredestination());
        map.handleBorder(animal);
        assertEquals(new Vector2d(0, 2), animal.getPosition());
    }

    @Test
    void animalShouldNotMoveBeyondTopBorder() {
        EarthMap map = new EarthMap(10, 5, new FertileEquator(10, 5), new TotalPredestination());
        Animal animal = new Animal(new Vector2d(5, 4), 100, 5, 50, 20, 1, 3, new TotalPredestination());
        map.handleBorder(animal);
        assertEquals(new Vector2d(5, 4), animal.getPosition());
    }

    @Test
    void animalShouldNotMoveBeyondBottomBorder() {
        EarthMap map = new EarthMap(10, 5, new FertileEquator(10, 5), new TotalPredestination());
        Animal animal = new Animal(new Vector2d(5, 5), 100, 5, 50, 20, 1, 3, new TotalPredestination());
        Vector2d newPosition = map.handleBorder(animal);
        assertEquals(new Vector2d(5, 4), newPosition);
        assertEquals(MapDirection.SOUTH, animal.getOrientation());
    }

    @Test
    void animalShouldNotMoveBeyondBottomLeftCorner() {
        EarthMap map = new EarthMap(10, 5, new FertileEquator(10, 5), new TotalPredestination());
        Animal animal = new Animal(new Vector2d(0, 0), 100, 5, 50, 20, 1, 3, new TotalPredestination());
        animal.setPosition(new Vector2d(-1, -1));
        map.handleBorder(animal);
        assertEquals(new Vector2d(9, 0), animal.getPosition());
        assertEquals(MapDirection.SOUTH, animal.getOrientation());
    }

    @Test
    void animalShouldNotMoveBeyondTopRightCorner() {
        EarthMap map = new EarthMap(10, 5, new FertileEquator(10, 5), new TotalPredestination());
        Animal animal = new Animal(new Vector2d(10, 4), 100, 5, 50, 20, 1, 3, new TotalPredestination());
        map.handleBorder(animal);
        assertEquals(new Vector2d(0, 4), animal.getPosition());
    }
}