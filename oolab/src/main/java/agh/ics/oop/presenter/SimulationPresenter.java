package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import agh.ics.oop.model.AbstractWorldMap;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class SimulationPresenter implements MapChangeListener {

    @FXML
    private Label infoLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private TextField movesInput;
    @FXML
    private GridPane mapGrid;

    private AbstractWorldMap worldMap;



    public void setWorldMap(AbstractWorldMap map) {
        this.worldMap = map;
    }

    public void drawMap() {
        Vector2d lowerLeft = worldMap.getCurrentBounds().lowerLeft();
        Vector2d upperRight = worldMap.getCurrentBounds().upperRight();
        int CELL_WIDTH = 25;
        int CELL_HEIGHT = 25;
        int rows = upperRight.getY() - lowerLeft.getY() + 1;
        int cols = upperRight.getX() - lowerLeft.getX() + 1;

        clearGrid();

        for (int i = 0; i < cols + 1; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }
        for (int i = 0; i < rows + 1; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }
        GridPane.setHalignment(infoLabel, HPos.CENTER);

        Label axisLabel = new Label("y\\x");
        GridPane.setHalignment(axisLabel, HPos.CENTER);
        mapGrid.add(axisLabel, 0, 0);

        for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++) {
            Label xLabel = new Label(Integer.toString(x));
            GridPane.setHalignment(xLabel, HPos.CENTER);
            mapGrid.add(xLabel, x - lowerLeft.getX() + 1, 0);
        }

        for (int y = lowerLeft.getY(); y <= upperRight.getY(); y++) {
            Label yLabel = new Label(Integer.toString(y));
            GridPane.setHalignment(yLabel, HPos.CENTER);
            mapGrid.add(yLabel, 0, rows - (y - lowerLeft.getY()));
        }

        for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++) {
            for (int y = lowerLeft.getY(); y <= upperRight.getY(); y++) {
                Vector2d position = new Vector2d(x, y);

                int finalX = x;
                int finalY = y;
                worldMap.objectAt(position)
                        .ifPresent(element -> {
                            WorldElementBox elementBox = new WorldElementBox(element);
                            mapGrid.add(elementBox, finalX - lowerLeft.getX() + 1, rows - (finalY - lowerLeft.getY()));
                        });
            }
        }
    }



    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst()); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
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
        List<Vector2d> positions = List.of(new Vector2d(1,2), new Vector2d(3, 1));
        AbstractWorldMap map = new GrassField(10);

        startSimulationInNewWindow(directions, positions, map);
    }

    private void startSimulationInNewWindow(List<MoveDirection> directions, List<Vector2d> positions, AbstractWorldMap map) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("simulation.fxml"));
                BorderPane viewRoot = loader.load();

                SimulationPresenter presenter = loader.getController();
                presenter.setWorldMap(map);
                map.addObserver(presenter);

                Stage stage = new Stage();
                Scene scene = new Scene(viewRoot);
                stage.setTitle("New Simulation Window");
                stage.setScene(scene);
                stage.show();

                SimulationEngine engine = new SimulationEngine(List.of(new Simulation(positions, directions, map)));
                engine.runAsyncInThreadPool();

            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        });
    }
}