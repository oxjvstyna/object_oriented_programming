package agh.ics.oop.model;

public class TotalPredestination implements MoveVariant {

    public TotalPredestination() {
    }

    @Override
    public int getNextMoveIndex(Genome genome, int moveIndex) {
        return (moveIndex + 1) % genome.size();
    }

    @Override
    public String toString() {
        return "TotalPredestination";
    }
}
