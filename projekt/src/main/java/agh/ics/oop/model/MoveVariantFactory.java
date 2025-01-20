package agh.ics.oop.model;

public class MoveVariantFactory {

    public static MoveVariant create(String moveVariant) {
        return switch (moveVariant) {
            case "SlightMadness" -> new SlightMadness();
            case "TotalPredestination" -> new TotalPredestination();
            default -> throw new IllegalArgumentException("Unknown move variant: " + moveVariant);
        };
    }
}
