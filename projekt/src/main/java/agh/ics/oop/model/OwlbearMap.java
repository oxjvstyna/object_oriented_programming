package agh.ics.oop.model;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.RandomPositionGenerator;

public class OwlbearMap extends AbstractWorldMap {

    public OwlbearMap(int width, int height, GrowthVariant variant) throws IncorrectPositionException {
        super(width, height, variant);
        generateOwlbearTerritory();
    }

    public void generateOwlbearTerritory() throws IncorrectPositionException {
        int sideLength = (int) Math.sqrt(0.2 * this.width * this.height);

        RandomPositionGenerator areaGenerator = new RandomPositionGenerator(
                width - sideLength + 1, height - sideLength + 1,
                0, 0, 1
        );
        Vector2d lowerLeft = areaGenerator.iterator().next();

        Vector2d upperRight = new Vector2d(
                lowerLeft.getX() + sideLength - 1,
                lowerLeft.getY() + sideLength - 1
        );

        RectangularMap owlbearTerritory = new RectangularMap(sideLength, sideLength, lowerLeft, upperRight, variant);

        RandomPositionGenerator generator = new RandomPositionGenerator(
                sideLength, sideLength, lowerLeft.getX(), lowerLeft.getY(), 1
        );
        Vector2d owlbearPosition = generator.iterator().next();
        owlbearTerritory.place(new Owlbear(owlbearPosition, 999999999, 5, 99999, 99999, 9999, 9999 ));
    }

}
