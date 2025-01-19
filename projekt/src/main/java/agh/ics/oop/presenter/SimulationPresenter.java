package agh.ics.oop.presenter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import javafx.util.Duration;
import agh.ics.oop.*;
import agh.ics.oop.model.*;

public class SimulationPresenter {

    @FXML
    private GridPane mapGrid;

    private boolean isRunning = false;
    private Timeline timeline;
    private Simulation simulation;

    public void initializeSimulation(SimulationEngine engine) {
        this.simulation = engine.getSimulation();
        startSimulation();
    }

    private void startSimulation() {
        this.simulation.run();
        timeline = new Timeline(new KeyFrame(Duration.millis(simulation.getSimConfig().simulationSpeed()), event -> {
            simulation.runStep();
            renderMap();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        isRunning = true;
    }

    @FXML
    private void pauseSimulation() {
        if (timeline != null && isRunning) {
            timeline.pause();
            isRunning = false;
        }
    }

    @FXML
    private void resumeSimulation() {
        if (timeline != null && !isRunning) {
            timeline.play();
            isRunning = true;
        }
    }

    private void renderMap() {
        AbstractWorldMap map = simulation.getSimConfig().currentMap();
        double cellSize = Math.min(mapGrid.getWidth() / map.getWidth(), mapGrid.getHeight() / map.getHeight());

        Canvas canvas = new Canvas(mapGrid.getWidth(), mapGrid.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int animalCount = map.getAnimalCountAt(x, y);
                boolean hasPlant = map.hasPlantAt(x, y);

                if (animalCount > 0) {
                    gc.setFill(Color.web(getAnimalColor(animalCount)));
                } else if (hasPlant) {
                    gc.setFill(Color.GREEN);
                } else {
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }

        mapGrid.getChildren().clear();
        mapGrid.add(canvas, 0, 0);
    }

    private String getAnimalColor(int animalCount) {
        // Im więcej zwierząt, tym ciemniejszy lub cieplejszy kolor
        int maxAnimals = 10; // Przyjmij maksymalną liczbę zwierząt na pole
        double intensity = Math.min(1.0, animalCount / (double) maxAnimals); // Normalizacja do zakresu 0-1
        int red = (int) (255 * intensity);
        int green = 255 - red;
        return String.format("rgb(%d, %d, 0)", red, green); // Gradient od zielonego (mało zwierząt) do czerwonego (dużo)
    }
    @FXML
    public void generateReport() {
        simulation.getSimConfig().currentMap().getReport();
    }
}