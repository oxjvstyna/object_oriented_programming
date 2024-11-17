package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final int maxWidth;
    private final int maxHeight;
    private final int grassCount;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.grassCount = grassCount;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new PositionIterator();
    }

    private class PositionIterator implements Iterator<Vector2d> {
        private final Set<Vector2d> usedPositions = new HashSet<>();
        private int generatedCount = 0;
        private final Random random = new Random();

        @Override
        public boolean hasNext() {
            return generatedCount < grassCount;
        }

        @Override
        public Vector2d next() {
            Vector2d position;
            do {
                int x = random.nextInt(maxWidth);
                int y = random.nextInt(maxHeight);
                position = new Vector2d(x, y);
            } while (usedPositions.contains(position));

            usedPositions.add(position);
            generatedCount++;
            return position;
        }
    }
}