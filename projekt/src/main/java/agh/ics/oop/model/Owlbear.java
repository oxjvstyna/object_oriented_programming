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
        if (this.getPosition().precedes(upperRight) && this.getPosition().precedes(lowerLeft)) {
            validator = position -> true;
        } else {
            validator = position -> false;
        }
        super.move(validator);
        };
}
