package agh.ics.oop.model;

public class Owlbear extends Animal {
    OwlbearMap worldMap;

    public Owlbear(Vector2d owlbearPosition, int initialEnergy, int genomeLength, int reproductionEnergy, int birthEnergy, int minMutation, int maxMutation, MoveVariant moveVariant, OwlbearMap map) {
        super(owlbearPosition, initialEnergy, genomeLength, reproductionEnergy, birthEnergy, minMutation, maxMutation, moveVariant);
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
