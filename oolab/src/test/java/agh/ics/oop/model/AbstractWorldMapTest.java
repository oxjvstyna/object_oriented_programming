package agh.ics.oop.model;
import agh.ics.oop.model.util.IncorrectPositionException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractWorldMapTest {

    @Test
    void testGetOrderedAnimals() throws IncorrectPositionException {
        // given
        AbstractWorldMap map = new RectangularMap(10, 10); // Zakładam, że EarthMap rozszerza AbstractWorldMap

        // when
        Animal animal1 = new Animal(new Vector2d(2, 3));
        Animal animal2 = new Animal(new Vector2d(1, 1));
        Animal animal3 = new Animal(new Vector2d(1, 2));
        Animal animal4 = new Animal(new Vector2d(3, 0));

        map.place(animal1);
        map.place(animal2);
        map.place(animal3);
        map.place(animal4);

        // then
        List<Animal> sortedAnimals = map.getOrderedAnimals();

        assertEquals(new Vector2d(1, 1), sortedAnimals.get(0).getPosition());
        assertEquals(new Vector2d(1, 2), sortedAnimals.get(1).getPosition());
        assertEquals(new Vector2d(2, 3), sortedAnimals.get(2).getPosition());
        assertEquals(new Vector2d(3, 0), sortedAnimals.get(3).getPosition());
    }
}
