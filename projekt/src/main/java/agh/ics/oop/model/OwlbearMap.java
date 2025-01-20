package agh.ics.oop.model;
import agh.ics.oop.model.util.RandomPositionGenerator;

public class OwlbearMap extends AbstractWorldMap implements MapVariant {
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

        territoryUpperRight = new Vector2d(territoryLowerLeft.x() + sideLength - 1, territoryLowerLeft.y() + sideLength - 1);

        RandomPositionGenerator generator = new RandomPositionGenerator(sideLength, sideLength, territoryLowerLeft.x(), territoryLowerLeft.y(), 1);
        Vector2d owlbearPosition = generator.iterator().next();
        AnimalConfig owlbearConfig = new AnimalConfig( 9999999, 5, 999999, 9999999, config.minMutation(), config.maxMutation(), config.moveVariant());
        this.owlbear = new Owlbear(owlbearPosition, owlbearConfig, this);
        this.place(owlbear);
    }

    public void eatAnimals() {
        for (Animal animal : occupiedFields.get(owlbear.getPosition())) {
            if (animal instanceof Owlbear) {
                continue;
            }
            animal.addEnergy(-9999999);
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
    @Override
    public String toString() {
        return "OwlbearMap";
    }
}
