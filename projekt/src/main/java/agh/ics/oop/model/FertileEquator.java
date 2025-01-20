package agh.ics.oop.model;

import java.util.HashSet;
import java.util.Set;

public class FertileEquator implements GrowthVariant{
    private final int width;
    private final int height;
    final Set<Vector2d> preferredFields = new HashSet<>();

    public FertileEquator(int width, int height) {
        this.width = width;
        this.height = height;
    }


    public Set<Vector2d> generateFields(){
        int equatorStartY = height / 2 - (int)(height * 0.1);
        int equatorEndY = height / 2 + (int)(height * 0.1);

        for (int x = 0; x < width; x++) {
            for (int y = equatorStartY; y <= equatorEndY; y++) {
                preferredFields.add(new Vector2d(x, y));
            }
        }
        return preferredFields;
    }

    @Override
    public String toString() {
        return "FertileEquator";
    }

}
