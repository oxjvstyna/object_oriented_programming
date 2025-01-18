package agh.ics.oop.model;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.List;

public class OwlbearMap extends AbstractWorldMap {
    Owlbear owlbear;
    Vector2d territoryLowerLeft;
    Vector2d territoryUpperRight;
    public OwlbearMap(int width, int height, GrowthVariant growthVariant, AnimalConfig config) {
        super(width, height, growthVariant, config);
        generateOwlbearTerritory();
    }

    public void generateOwlbearTerritory() {
        int sideLength = (int) Math.sqrt(0.2 * this.width * this.height);

        RandomPositionGenerator areaGenerator = new RandomPositionGenerator(width - sideLength + 1, height - sideLength + 1, 0, 0, 1);
        territoryLowerLeft = areaGenerator.iterator().next();

        territoryUpperRight = new Vector2d(lowerLeft.getX() + sideLength - 1, lowerLeft.getY() + sideLength - 1);

        RandomPositionGenerator generator = new RandomPositionGenerator(sideLength, sideLength, territoryLowerLeft.getX(), territoryUpperRight.getY(), 1);
        Vector2d owlbearPosition = generator.iterator().next();
        AnimalConfig owlbearConfig = new AnimalConfig( 999999999, 5, 99999, 99999, 9999, 9999, moveVariant);
        this.owlbear = new Owlbear(owlbearPosition, owlbearConfig, this);
        this.place(owlbear);
    }

    public void eatAnimals() {
        for (Animal animal : occupiedFields.get(owlbear.getPosition())) {
            animal.addEnergy(-999999999);
        }
    }

    public Vector2d getTerritoryLowerLeft() {
        return territoryLowerLeft;
    }

    public Vector2d getTerritoryUpperRight() {
        return territoryUpperRight;
    }

    @Override
    public void handleMap() {
        removeAnimals();
        moveAnimals();
        eatAnimals();
        consumePlants();
        reproduceAnimals();
        growPlants();
    }
}
