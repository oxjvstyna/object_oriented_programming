package agh.ics.oop.model;

public class Owlbear extends Animal {
    OwlbearMap worldMap;

    public Owlbear(Vector2d owlbearPosition,AnimalConfig config, OwlbearMap map) {
        super(owlbearPosition, config);
        this.worldMap = map;
    }
    
    @Override
    public void move(MoveValidator validator) {
        Vector2d upperRight = worldMap.getTerritoryUpperRight();
        Vector2d lowerLeft = worldMap.getTerritoryLowerLeft();

        // Ustaw walidator pozycji
        validator = position -> position.precedes(upperRight) && position.follows(lowerLeft);

        // Wywołaj metodę move z klas bazowych
        super.move(validator);
        this.addEnergy(worldMap.getPlantEnergy());
    }

}
