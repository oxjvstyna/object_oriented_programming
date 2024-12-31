package agh.ics.oop.model;

import java.util.List;

public class SimulationConfig {
    private final List<MoveDirection> moves;
    private final AbstractWorldMap currentMap;
    private final GrowthVariant growthVariant;

    public SimulationConfig(
            AbstractWorldMap map,
            GrowthVariant growthVariant,
            List<Animal> animals,
            List<MoveDirection> moves,
            AbstractWorldMap currentMap) {
        this.moves = moves;
        this.currentMap = currentMap;
        this.growthVariant = growthVariant;
    }
    public List<MoveDirection> getMoves() {
        return moves;
    }
    public AbstractWorldMap getCurrentMap() {
        return currentMap;
    }
    public GrowthVariant getGrowthVariant() {
        return growthVariant;
    }

}
