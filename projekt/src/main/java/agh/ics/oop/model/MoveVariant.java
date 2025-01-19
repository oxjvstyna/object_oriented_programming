package agh.ics.oop.model;

import agh.ics.oop.model.Genome;
import agh.ics.oop.model.TotalPredestination;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TotalPredestination.class, name = "TotalPredestination"),
        @JsonSubTypes.Type(value = SlightMadness.class, name = "SlightMadness"),
})

public interface MoveVariant {

    int getNextMoveIndex(Genome genome, int moveIndex);
}