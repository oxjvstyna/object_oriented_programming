package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final ArrayList<Vector2d> positions;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int lowerLeftX, int lowerLeftY, int positionCount) {
        this.positions = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < positionCount; i++) {
            int x = lowerLeftX + random.nextInt(maxWidth);
            int y = lowerLeftY + random.nextInt(maxHeight);
            positions.add(new Vector2d(x, y)); // Dodawanie punktów bez sprawdzania ich unikalności
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
            return internalIterator.next();
        }
    }
}
