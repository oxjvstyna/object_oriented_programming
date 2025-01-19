package agh.ics.oop.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Set;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,   // Jackson użyje wartości pola "type" do wyboru klasy
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"  // JSON musi zawierać pole "type": "ConcreteGrowthVariant"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FertileEquator.class, name = "FertileEquator"),
})
public interface GrowthVariant {
    public Set<Vector2d> generateFields();
}
