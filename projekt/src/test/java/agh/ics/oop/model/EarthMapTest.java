package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EarthMapTest {

    @Test
    void animalShouldWrapAroundHorizontallyWhenExitingLeft() {
        TotalPredestination moveVariant = new TotalPredestination();
        AnimalConfig animalConfig = new AnimalConfig(100, 5, 50, 20, 1, 3, moveVariant);
        EarthMap map = new EarthMap(10, 5, new FertileEquator(10, 5), animalConfig);
        Animal animal = new Animal(new Vector2d(0, 2), animalConfig);
        animal.setPosition(new Vector2d(-1, 2));
        map.handleBorder(animal);
        assertEquals(new Vector2d(9, 2), animal.getPosition());
    }

    @Test
    void animalShouldWrapAroundHorizontallyWhenExitingRight() {
        TotalPredestination moveVariant = new TotalPredestination();
        AnimalConfig animalConfig = new AnimalConfig(100, 5, 50, 20, 1, 3, moveVariant);
        EarthMap map = new EarthMap(10, 5, new FertileEquator(10, 5), animalConfig);
        Animal animal = new Animal(new Vector2d(10, 2), animalConfig);
        animal.setPosition(new Vector2d(11, 2));
        map.handleBorder(animal);
        assertEquals(new Vector2d(0, 2), animal.getPosition());
    }

    @Test
    void animalShouldNotMoveBeyondTopBorder() {
        TotalPredestination moveVariant = new TotalPredestination();
        AnimalConfig animalConfig = new AnimalConfig(100, 5, 50, 20, 1, 3, moveVariant);
        EarthMap map = new EarthMap(10, 5, new FertileEquator(10, 5), animalConfig);
        Animal animal = new Animal(new Vector2d(5, 4), animalConfig);
        map.handleBorder(animal);
        assertEquals(new Vector2d(5, 4), animal.getPosition());
    }

    @Test
    void animalShouldNotMoveBeyondBottomBorder() {
        TotalPredestination moveVariant = new TotalPredestination();
        AnimalConfig animalConfig = new AnimalConfig(100, 5, 50, 20, 1, 3, moveVariant);
        EarthMap map = new EarthMap(10, 5, new FertileEquator(10, 5), animalConfig);
        Animal animal = new Animal(new Vector2d(5, 0), animalConfig);
        animal.setPosition(new Vector2d(5, -1));
        map.handleBorder(animal);
        assertEquals(new Vector2d(5, 0), animal.getPosition());
    }

    @Test
    void animalShouldWrapAroundBottomLeftCorner() {
        TotalPredestination moveVariant = new TotalPredestination();
        AnimalConfig animalConfig = new AnimalConfig(100, 5, 50, 20, 1, 3, moveVariant);
        EarthMap map = new EarthMap(10, 5, new FertileEquator(10, 5), animalConfig);
        Animal animal = new Animal(new Vector2d(0, 0), animalConfig);
        animal.setPosition(new Vector2d(-1, -1));
        map.handleBorder(animal);
        assertEquals(new Vector2d(9, 0), animal.getPosition());
    }

    @Test
    void animalShouldWrapAroundTopRightCorner() {
        TotalPredestination moveVariant = new TotalPredestination();
        AnimalConfig animalConfig = new AnimalConfig(100, 5, 50, 20, 1, 3, moveVariant);
        EarthMap map = new EarthMap(10, 5, new FertileEquator(10, 5), animalConfig);
        Animal animal = new Animal(new Vector2d(9, 4), animalConfig);
        animal.setPosition(new Vector2d(10, 5));
        map.handleBorder(animal);
        assertEquals(new Vector2d(0, 4), animal.getPosition());
    }
}