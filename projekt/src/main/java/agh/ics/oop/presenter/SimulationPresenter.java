package agh.ics.oop.presenter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
                int animalCount = map.getMaxEnergyAt(x, y);
                boolean hasPlant = map.hasPlantAt(x, y);

                if (animalCount > 0) {
                    gc.setFill(Color.web(getAnimalColor(animalCount)));
                } else if (hasPlant) {
                    gc.setFill(Color.GREEN);
                } else {
                    gc.setFill(Color.LIGHTGREEN);
                }
                gc.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }

        mapGrid.getChildren().clear();
        mapGrid.add(canvas, 0, 0);
    }

    // Kolor dla zwierząt na podstawie energii
    private String getAnimalColor(int energy) {
        int maxEnergy = 100;
        double intensity = Math.min(1.0, energy / (double) maxEnergy); // Normalizacja do zakresu 0-1

        // Gradient od jasnoszarego (wysoka energia) do czarnego (niska energia)
        int gray = (int) (211 - 211 * (1.0 - intensity)); // Energia 0 -> 0 (czarny), energia max -> 211 (jasnoszary)
        return String.format("rgb(%d, %d, %d)", gray, gray, gray);
    }




    @FXML
    public void generateReport() {
        simulation.getSimConfig().currentMap().getReport();
    }
}