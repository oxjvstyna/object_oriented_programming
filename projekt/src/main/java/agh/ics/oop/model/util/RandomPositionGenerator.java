package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final ArrayList<Vector2d> positions;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int positionCount) {

        this.positions = new ArrayList<>();

        for (int x = 0; x < maxWidth; x++) {
            for (int y = 0; y < maxHeight; y++) {
                positions.add(new Vector2d(x, y));
            }
        }

        Collections.shuffle(positions);

        while (positions.size() > positionCount) {
            positions.removeLast();
        }
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new PositionIterator();
    }

    private class PositionIterator implements Iterator<Vector2d> {
        private final Iterator<Vector2d> internalIterator = positions.iterator();

        @Override
        public boolean hasNext() {
            return internalIterator.hasNext();
        }

        @Override
        public Vector2d next() {
            return  internalIterator.next();
        }
    }
}