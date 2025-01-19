package agh.ics.oop.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,    // Używamy nazwy klasy w JSON jako identyfikatora
        include = JsonTypeInfo.As.PROPERTY,  // JSON powinien zawierać pole "type"
        property = "type"  // Klucz w JSON, np. "type": "GlobeMap"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GlobeMap.class, name = "GlobeMap"),
        @JsonSubTypes.Type(value = OwlbearMap.class, name = "OwlbearMap")
})
public interface MapVariant {
}
