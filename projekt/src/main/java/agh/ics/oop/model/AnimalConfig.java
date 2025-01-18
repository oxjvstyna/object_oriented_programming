
package agh.ics.oop.model;

public class AnimalConfig {
    private final Vector2d initialPosition;
    private final int initialEnergy;
    private final int genomeLength;
    private final int reproductionEnergy;
    private final int birthEnergy;
    private final int minMutation;
    private final int maxMutation;
    private final MoveVariant moveVariant;

    public AnimalConfig(
            Vector2d initialPosition,
            int initialEnergy,
            int genomeLength,
            int reproductionEnergy,
            int birthEnergy,
            int minMutation,
            int maxMutation,
            MoveVariant moveVariant) {
        this.initialPosition = initialPosition;
        this.initialEnergy = initialEnergy;
        this.genomeLength = genomeLength;
        this.reproductionEnergy = reproductionEnergy;
        this.birthEnergy = birthEnergy;
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;
        this.moveVariant = moveVariant;
    }

    public Vector2d getInitialPosition() {
        return initialPosition;
    }

    public int getInitialEnergy() {
        return initialEnergy;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public int getReproductionEnergy() {
        return reproductionEnergy;
    }

    public int getBirthEnergy() {
        return birthEnergy;
    }

    public int getMinMutation() {
        return minMutation;
    }

    public int getMaxMutation() {
        return maxMutation;
    }

    public MoveVariant getMoveVariant() {
        return moveVariant;
    }
}
