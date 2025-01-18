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

        // Ustaw walidator pozycji
        validator = position -> position.precedes(upperRight) && position.follows(lowerLeft);

        // Wywołaj metodę move z klas bazowych
        super.move(validator);
        this.addEnergy(1);
    }

}
