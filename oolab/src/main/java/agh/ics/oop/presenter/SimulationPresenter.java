package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import agh.ics.oop.model.AbstractWorldMap;
import javafx.scene.control.TextField;
import javafx.application.Application;

import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    @FXML
    private Label infoLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private TextField movesInput;
    @FXML
    private Button startButton;

    private AbstractWorldMap worldMap;


    public void setWorldMap(AbstractWorldMap map) {
        this.worldMap = map;
    }

    public void drawMap() {
        infoLabel.setText(worldMap.toString());
    }


    @Override
    public void mapChanged(AbstractWorldMap map, String message) {
        Platform.runLater(() -> {
            setWorldMap(map);
            drawMap();
            descriptionLabel.setText(message);
        });
    }

    @FXML
    public void onSimulationStartClicked(ActionEvent actionEvent) {
        String moveList = movesInput.getText();
        List<MoveDirection> directions = OptionsParser.parse(moveList.split(" "));
        List<Vector2d> positions = List.of(new Vector2d(1,2), new Vector2d(3, 4));
        AbstractWorldMap map = new GrassField(10);
        map.addObserver(this);
        SimulationEngine engine = new SimulationEngine(List.of(new Simulation(positions, directions, map)));
        new Thread(engine::runSync).start();
    }
}
