package agh.ics.oop.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Random;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SlightMadness.class, name = "SlightMadness"),
        @JsonSubTypes.Type(value = TotalPredestination.class, name = "TotalPredestination")
})
public class SlightMadness implements MoveVariant {
    public SlightMadness() {
    }
    private final Random randomGenerator = new Random();

    @Override
    public int getNextMoveIndex(Genome genome, int currentMoveIndex) {
        if (randomGenerator.nextDouble() < 0.8) {
            return (currentMoveIndex + 1) % genome.size();
        } else {
            return randomGenerator.nextInt(genome.size());
        }
    }
    @Override
    public String toString() {
        return "SlightMadness";
    }
}
