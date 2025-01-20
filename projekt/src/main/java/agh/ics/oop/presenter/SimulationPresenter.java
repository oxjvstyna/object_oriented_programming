package agh.ics.oop.presenter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import agh.ics.oop.*;
import agh.ics.oop.model.*;

public class SimulationPresenter {
    @FXML
    private TextField animalIdField;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label followedAnimalEnergyLabel, followedAnimalBirthDayLabel, followedAnimalDeathDayLabel,
            followedAnimalChildrenCountLabel, followedAnimalDescendantsCountLabel, followedAnimalGenotypeLabel,
            followedAnimalActiveGeneIndexLabel, followedAnimalPlantsEatenLabel;
    @FXML
    private TextArea animalStatusTextArea;

    private boolean isRunning = false;
    private Timeline timeline;
    private Simulation simulation;
    private int currentDay;
    private final AnimalTracker tracker = new AnimalTracker();

    public void initializeSimulation(SimulationEngine engine) {
        this.simulation = engine.getSimulation();
        startSimulation();
    }

    private void startSimulation() {
        this.simulation.run();
        timeline = new Timeline(new KeyFrame(Duration.millis(simulation.getSimConfig().simulationSpeed()), event -> {
            simulation.runStep();
            renderMap();
            currentDay++;
            updateAnimalStatus();
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

    public void updateAnimalStatus() {
        Animal followedAnimal = tracker.getTrackedAnimal();
        if (tracker.isTracking() && followedAnimal != null) {
            followedAnimalPlantsEatenLabel.setText(String.valueOf(tracker.getPlantsEaten()));
            followedAnimalDescendantsCountLabel.setText(String.valueOf(tracker.getDescendantsCount()));
            followedAnimalBirthDayLabel.setText(String.valueOf(followedAnimal.getAge()));
            followedAnimalDeathDayLabel.setText(tracker.getDeathDay() == -1 ? "Still alive" : String.valueOf(tracker.getDeathDay()));
            followedAnimalEnergyLabel.setText(String.valueOf(followedAnimal.getEnergy()));
            followedAnimalChildrenCountLabel.setText(String.valueOf(followedAnimal.getNumberOfChildren()));
            followedAnimalGenotypeLabel.setText(followedAnimal.getGenomes().toString());
            followedAnimalActiveGeneIndexLabel.setText(String.valueOf(followedAnimal.getMoveIndex()));
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

    private String getAnimalColor(int energy) {
        int maxEnergy = 100; // Załóżmy, że 100 to maksymalna energia
        double ratio = Math.min(1.0, energy / (double) maxEnergy);

        // Kolor przechodzi od czerwonego (niska energia) do zielonego (wysoka energia)
        int red = (int) (255 * (1 - ratio)); // Więcej energii = mniej czerwonego
        int green = (int) (255 * ratio);    // Więcej energii = więcej zielonego
        int blue = 0; // Brak niebieskiego

        return String.format("rgb(%d, %d, %d)", red, green, blue);
    }


    @FXML
    public void generateReport() {
        simulation.getSimConfig().currentMap().getReport();
    }

    @FXML
    private void trackAnimalById() {
        String inputId = animalIdField.getText().trim();
        if (inputId.isEmpty()) {
            System.out.println("Please enter a valid ID.");
            return;
        }

        try {
            int animalId = Integer.parseInt(inputId);
            Animal animal = simulation.getSimConfig().currentMap().getAnimalById(animalId);

            if (animal != null) {
                tracker.startTracking(animal);
                System.out.println("Started tracking animal with ID: " + animalId);
                updateAnimalStatus();
            } else {
                System.out.println("No animal found with ID: " + animalId);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid ID format. Please enter a valid ID.");
        }
    }
}