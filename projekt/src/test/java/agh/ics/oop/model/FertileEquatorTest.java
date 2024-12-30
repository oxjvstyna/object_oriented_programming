package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FertileEquatorTest {

    @Test
    void testGenerateFields() {
        // Przygotowanie
        FertileEquator equator = new FertileEquator(10, 10);

        // Generowanie preferowanych pól
        Set<Vector2d> fields = equator.generateFields();

        // Obliczamy liczbę preferowanych pól (20% powierzchni mapy)
        int expectedPreferredCount = (int) (0.2 * 10 * 10); // 20% z 100 pól = 20
        assertEquals(expectedPreferredCount, fields.size(), "Liczba preferowanych pól powinna wynosić 20.");
    }

    @Test
    void testGenerateFieldsCorrectRange() {
        // Przygotowanie
        FertileEquator equator = new FertileEquator(10, 10);
        Set<Vector2d> fields = equator.generateFields();

        // Sprawdzamy, czy wszystkie preferowane pola mieszczą się w odpowiednim zakresie
        int equatorStartY = 10 / 2 - (int)(10 * 0.1); // 4
        int equatorEndY = 10 / 2 + (int)(10 * 0.1); // 6

        for (Vector2d field : fields) {
            int y = field.getY();
            assertTrue(y >= equatorStartY && y <= equatorEndY,
                    "Pole " + field + " powinno być w obrębie równika.");
        }
    }

    @Test
    void testFieldWithinEquator() {
        // Przygotowanie
        FertileEquator equator = new FertileEquator(10, 10);
        Set<Vector2d> fields = equator.generateFields();

        // Testowanie, czy pole w obrębie równika jest preferowane
        Vector2d equatorField = new Vector2d(5, 5); // (5,5) w obrębie równika
        assertTrue(fields.contains(equatorField), "Pole (5,5) powinno być preferowane.");
    }

    @Test
    void testFieldOutsideEquator() {
        // Przygotowanie
        FertileEquator equator = new FertileEquator(10, 10);
        Set<Vector2d> fields = equator.generateFields();

        // Testowanie, czy pole spoza równika nie jest preferowane
        Vector2d nonEquatorField = new Vector2d(0, 0); // pole (0,0) spoza równika
        assertFalse(fields.contains(nonEquatorField), "Pole (0,0) nie powinno być preferowane.");
    }

    @Test
    void testRandomFieldsGeneration() {
        // Przygotowanie
        FertileEquator equator = new FertileEquator(10, 10);
        Set<Vector2d> fields = equator.generateFields();

        // Sprawdzamy, czy liczba wygenerowanych pól jest zgodna z oczekiwaniami
        int equatorStartY = 10 / 2 - (int)(10 * 0.1); // 4
        int equatorEndY = 10 / 2 + (int)(10 * 0.1); // 6

        int preferredCount = 0;
        for (Vector2d field : fields) {
            int y = field.getY();
            if (y >= equatorStartY && y <= equatorEndY) {
                preferredCount++;
            }
        }

        // Sprawdzamy, czy 20% pól na mapie jest preferowanych
        int totalFields = 10 * 10; // 100 pól na mapie
        assertTrue(preferredCount >= totalFields * 0.18 && preferredCount <= totalFields * 0.22,
                "Preferowane pola powinny stanowić około 20% wszystkich pól na mapie.");
    }
}
