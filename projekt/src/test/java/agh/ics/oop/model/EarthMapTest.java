package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EarthMapTest {

    @Test
    void animalShouldWrapAroundHorizontallyWhenExitingLeft() {
        // given
        MoveValidator validator = position -> true; // Always allows movement
        GrowthVariant variant = new FertileEquator(10, 5);
        EarthMap map = new EarthMap(10, 5, variant, new TotalPredestination()); // mapa o szerokości 10 i wysokości 5
        Vector2d initialPosition = new Vector2d(0, 2);  // zwierzę na lewej krawędzi
        Animal animal = new Animal(initialPosition, 100, 5, 50, 20, 1, 3, new TotalPredestination());

        // when
        animal.move(validator);
        animal.move(validator);
        map.handleBorder(animal);  // zwierzę ma wyjść za lewą krawędź

        // then
        assertEquals(new Vector2d(9, 2), animal.getPosition());  // zwierzę powinno pojawić się po prawej stronie
    }

    @Test
    void animalShouldWrapAroundHorizontallyWhenExitingRight() {
        // given
        GrowthVariant variant = new FertileEquator(10, 5);
        MoveVariant moveVariant = new TotalPredestination();
        EarthMap map = new EarthMap(10, 5, variant, moveVariant); // mapa o szerokości 10 i wysokości 5
        Vector2d initialPosition = new Vector2d(11, 2);  // zwierzę na prawej krawędzi
        Animal animal = new Animal(initialPosition, 100, 5, 50, 20, 1, 3, moveVariant);

        // when
        map.handleBorder(animal);  // zwierzę ma wyjść za prawą krawędź

        // then
        assertEquals(new Vector2d(0, 2), animal.getPosition());  // zwierzę powinno pojawić się po lewej stronie
    }

    @Test
    void animalShouldNotMoveBeyondTopBorder() {
        // given
        MoveValidator validator = position -> true; // Always allows movement
        GrowthVariant variant = new FertileEquator(10, 5);
        MoveVariant moveVariant = new TotalPredestination();
        EarthMap map = new EarthMap(10, 5, variant, moveVariant); // mapa o szerokości 10 i wysokości 5
        Vector2d initialPosition = new Vector2d(5, 4);  // zwierzę blisko górnej krawędzi
        Animal animal = new Animal(initialPosition, 100, 5, 50, 20, 1, 3, moveVariant);

        // when
        animal.move(validator);
        Vector2d newPosition = map.handleBorder(animal);  // zwierzę ma wyjść poza górną krawędź

        // then
        assertEquals(new Vector2d(5, 4), newPosition);  // zwierzę nie powinno się ruszyć, ponieważ jest na biegunie
        assertEquals(MapDirection.SOUTH, animal.getOrientation());  // jego orientacja powinna zostać odwrócona
    }

    @Test
    void animalShouldNotMoveBeyondBottomBorder() {
        // given
        GrowthVariant variant = new FertileEquator(10, 5);
        MoveVariant moveVariant = new TotalPredestination();
        EarthMap map = new EarthMap(10, 5, variant, moveVariant); // mapa o szerokości 10 i wysokości 5
        Vector2d initialPosition = new Vector2d(5, 0);  // zwierzę blisko dolnej krawędzi
        Animal animal = new Animal(initialPosition, 100, 5, 50, 20, 1, 3, moveVariant);

        // when
        Vector2d newPosition = map.handleBorder(animal);  // zwierzę ma wyjść poza dolną krawędź

        // then
        assertEquals(new Vector2d(5, 0), newPosition);  // zwierzę nie powinno się ruszyć, ponieważ jest na biegunie
        assertEquals(MapDirection.NORTH, animal.getOrientation());  // jego orientacja powinna zostać odwrócona
    }

    @Test
    void animalShouldNotMoveBeyondBottomLeftCorner() {
        // given
        MoveValidator validator = position -> true; // Always allows movement
        GrowthVariant variant = new FertileEquator(10, 5);
        MoveVariant moveVariant = new TotalPredestination();
        EarthMap map = new EarthMap(10, 5, variant, moveVariant); // mapa o szerokości 10 i wysokości 5
        Vector2d initialPosition = new Vector2d(0, 0);  // zwierzę na dolnym lewym rogu
        Animal animal = new Animal(initialPosition, 100, 5, 50, 20, 1, 3, moveVariant);

        // when
        animal.move(validator);
        animal.move(validator);
        animal.move(validator);
        Vector2d newPosition = map.handleBorder(animal);  // zwierzę ma wyjść poza dolną lewą krawędź

        // then
        assertEquals(new Vector2d(9, 0), newPosition);  // zwierzę nie powinno się ruszyć
        assertEquals(MapDirection.NORTH_EAST, animal.getOrientation());  // jego orientacja powinna zostać odwrócona
    }

    @Test
    void animalShouldNotMoveBeyondTopRightCorner() {
        // given
        MoveValidator validator = position -> true; // Always allows movement
        GrowthVariant variant = new FertileEquator(10, 5);
        MoveVariant moveVariant = new TotalPredestination();
        EarthMap map = new EarthMap(10, 5, variant, moveVariant); // mapa o szerokości 10 i wysokości 5
        Vector2d initialPosition = new Vector2d(9, 4);  // zwierzę na górnym prawym rogu
        Animal animal = new Animal(initialPosition, 100, 5, 50, 20, 1, 3, moveVariant);

        // when
        animal.move(validator);
        System.out.println(animal.getPosition());
        animal.move(validator);
        Vector2d newPosition = map.handleBorder(animal);  // zwierzę ma zostać

        // then
        assertEquals(new Vector2d(0, 4), newPosition);
        assertEquals(MapDirection.SOUTH_WEST, animal.getOrientation());  // jego orientacja powinna zostać odwrócona
    }
}
