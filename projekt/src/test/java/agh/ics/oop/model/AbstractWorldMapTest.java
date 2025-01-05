package agh.ics.oop.model;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractWorldMapTest {

    @Test
    void testGetOrderedAnimals() {
        // given
        GrowthVariant growthVariant = new FertileEquator(10, 10);
        MoveVariant moveVariant = new TotalPredestination();
        AbstractWorldMap map = new EarthMap(10, 10, growthVariant, moveVariant); // Zakładam, że EarthMap rozszerza AbstractWorldMap

        // when
        Animal animal1 = new Animal(new Vector2d(2, 3), 10, 5, 10, 10, 1, 4, null);
        Animal animal2 = new Animal(new Vector2d(1, 1), 10, 5, 10, 10, 1, 4, null);
        Animal animal3 = new Animal(new Vector2d(1, 2), 10, 5, 10, 10, 1, 4, null);
        Animal animal4 = new Animal(new Vector2d(3, 0), 10, 5, 10, 10, 1, 4, null);

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
