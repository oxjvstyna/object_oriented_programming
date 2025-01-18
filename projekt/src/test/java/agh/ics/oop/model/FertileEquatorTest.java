package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class FertileEquatorTest {

    @Test
    void generateFieldsShouldCreateCorrectNumberOfPreferredFields() {
        // given
        int width = 10;
        int height = 10;
        FertileEquator equator = new FertileEquator(width, height);

        // when
        Set<Vector2d> preferredFields = equator.generateFields();

        // then
        int equatorStartY = height / 2 - (int) (height * 0.1);
        int equatorEndY = height / 2 + (int) (height * 0.1);
        int expectedCount = width * (equatorEndY - equatorStartY + 1);

        assertEquals(expectedCount, preferredFields.size(),
                "Number of preferred fields should match expected count.");
    }

    @Test
    void generateFieldsShouldIncludeOnlyCentralRows() {
        // given
        int width = 10;
        int height = 10;
        FertileEquator equator = new FertileEquator(width, height);

        // when
        Set<Vector2d> preferredFields = equator.generateFields();

        // then
        int equatorStartY = height / 2 - (int) (height * 0.1);
        int equatorEndY = height / 2 + (int) (height * 0.1);

        for (Vector2d field : preferredFields) {
            assertTrue(field.y() >= equatorStartY && field.y() <= equatorEndY,
                    "Field should belong to the equator region.");
        }
    }

    @Test
    void generateFieldsShouldCoverFullWidthOfMap() {
        // given
        int width = 10;
        int height = 10;
        FertileEquator equator = new FertileEquator(width, height);

        // when
        Set<Vector2d> preferredFields = equator.generateFields();

        // then
        int equatorStartY = height / 2 - (int) (height * 0.1);
        int equatorEndY = height / 2 + (int) (height * 0.1);

        for (int x = 0; x < width; x++) {
            for (int y = equatorStartY; y <= equatorEndY; y++) {
                assertTrue(preferredFields.contains(new Vector2d(x, y)),
                        "Preferred fields should include all positions in equator band.");
            }
        }
    }

    @Test
    void generateFieldsShouldNotIncludeFieldsOutsideEquatorBand() {
        // given
        int width = 10;
        int height = 10;
        FertileEquator equator = new FertileEquator(width, height);

        // when
        Set<Vector2d> preferredFields = equator.generateFields();

        // then
        int equatorStartY = height / 2 - (int) (height * 0.1);
        int equatorEndY = height / 2 + (int) (height * 0.1);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (y < equatorStartY || y > equatorEndY) {
                    assertFalse(preferredFields.contains(new Vector2d(x, y)),
                            "Fields outside equator band should not be preferred.");
                }
            }
        }
    }
}
