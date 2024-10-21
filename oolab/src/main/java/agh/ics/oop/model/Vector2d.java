package agh.ics.oop.model;

import java.util.Vector;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean precedes(Vector2d other) {
        return this.x < other.x && this.y <= other.y;
    }
    public boolean follows(Vector2d other) {
        return this.x > other.x && this.y >= other.y;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Vector2d upperRight(Vector2d other) {
        int x_add = Math.max(this.x, other.x);
        int y_add = Math.max(this.y, other.y);
        return new Vector2d(x_add, y_add);
    }
    public Vector2d lowerLeft(Vector2d other) {
        int x_add = Math.min(this.x, other.x);
        int y_add = Math.min(this.y, other.y);
        return new Vector2d(x_add, y_add);
    }
    public Vector2d opposite(Vector2d other) {
        return new Vector2d(-other.x, -other.y);
    }
    public boolean equals(Vector2d other) { //TO TRZEBA JAKOS ZMIENIC
        return this.x == other.x && this.y == other.y;
    }

}

