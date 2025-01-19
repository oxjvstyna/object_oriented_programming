package agh.ics.oop.model;

public class MoveVariantFactory {

    /**
     * Tworzy wariant ruchu na podstawie wybranego typu.
     *
     * @param moveVariant Wariant ruchu (np. "FullRandom", "ForwardOnly").
     * @return Strategia ruchu.
     * @throws IllegalArgumentException Jeśli wariant ruchu jest nieznany.
     */
    public static MoveVariant create(String moveVariant) {
        return switch (moveVariant) {
            case "SlightMadness" -> new SlightMadness();
            case "TotalPredestination" -> new TotalPredestination();
            default -> throw new IllegalArgumentException("Unknown move variant: " + moveVariant);
        };
    }
}
