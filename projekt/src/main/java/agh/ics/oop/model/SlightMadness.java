package agh.ics.oop.model;

import java.util.Random;

public class SlightMadness implements MoveVariant {
    private final Random randomGenerator = new Random();

    @Override
    public int getNextMoveIndex(Genome genome, int currentMoveIndex) {
        if (randomGenerator.nextDouble() < 0.8) {
            return (currentMoveIndex + 1) % genome.size();
        } else {
            return randomGenerator.nextInt(genome.size());
        }
    }
}
