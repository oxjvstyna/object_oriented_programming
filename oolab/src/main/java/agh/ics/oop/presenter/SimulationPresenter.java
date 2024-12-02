package agh.ics.oop.presenter;

import agh.ics.oop.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import agh.ics.oop.model.AbstractWorldMap;

public class SimulationPresenter {
    @FXML
    private Label infoLabel;
    private WorldMap<AbstractWorldMap, Vector2d> worldMap;

    public void setWorldMap(WorldMap<AbstractWorldMap, Vector2d> map){
        this.worldMap = map;
    }

    public void drawMap(){
        infoLabel.setText(worldMap.toString());
    }

    private void mapChanged(WorldMap<AbstractWorldMap, Vector2d> map) {
        setWorldMap(map);
        drawMap();
    }
}
