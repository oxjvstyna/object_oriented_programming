package agh.ics.oop.model;

public class TotalPredestination implements MoveVariant {

    @Override
    public int getNextMoveIndex(Genome genome, int moveIndex) {
        return (moveIndex + 1) % genome.size();
    }
}
