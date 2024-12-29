package agh.ics.oop.model;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.RandomPositionGenerator;

public class OwlbearMap extends AbstractWorldMap {

    public OwlbearMap(int width, int height) throws IncorrectPositionException {
        super(width, height);
        generateOwlbearTerritory();
    }

    public void generateOwlbearTerritory() throws IncorrectPositionException {
        int sideLength = (int) Math.sqrt(0.2 * this.width * this.height);

        RectangularMap owlbearTerritory = new RectangularMap(sideLength, sideLength);
        RandomPositionGenerator areaGenerator = new RandomPositionGenerator(width - sideLength - 1, height - sideLength - 1, 1);
        owlbearTerritory.lowerLeft = areaGenerator.iterator().next();
        owlbearTerritory.upperRight = (new Vector2d(lowerLeft.getX() + width, lowerLeft.getY() + height));
        RandomPositionGenerator generator = new RandomPositionGenerator(sideLength, sideLength, 1);
        owlbearTerritory.place(new Owlbear(generator.iterator().next()));
    }

}
