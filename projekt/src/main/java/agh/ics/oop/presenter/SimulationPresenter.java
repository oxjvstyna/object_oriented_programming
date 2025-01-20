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

import java.util.Arrays;
import java.util.Map;

public class SimulationPresenter {
    @FXML
    private TextField animalIdField;
    @FXML
    private TextArea reportTextArea;
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
            generateReport();
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

        String dominantGenome = map.getGenotypes().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");

        var preferredFields = map.getPreferredPlantFields();

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                boolean hasPlant = map.hasPlantAt(x, y);
                var animalsAtCell = map.getAnimalsAt(x, y);

                if (preferredFields.contains(new Vector2d(x, y))) {
                    gc.setFill(Color.DARKGREEN);
                }else if (hasPlant) {
                    gc.setFill(Color.GREEN);
                } else {
                    gc.setFill(Color.LIGHTGREEN);
                }
                gc.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);

                if (!animalsAtCell.isEmpty()) {
                    for (Animal animal : animalsAtCell) {
                        if (dominantGenome.equals(Arrays.toString(animal.getGenome().getGenesAsStrings()))) {
                            gc.setFill(Color.RED);
                        } else {
                            gc.setFill(Color.web(getAnimalColor(animal.getEnergy())));
                        }
                        gc.fillOval(
                                x * cellSize + cellSize * 0.2,
                                y * cellSize + cellSize * 0.2,
                                cellSize * 0.6,
                                cellSize * 0.6
                        );
                    }
                }
            }
        }

        mapGrid.getChildren().clear();
        mapGrid.add(canvas, 0, 0);
    }

    private String getAnimalColor(int energy) {
        int maxEnergy = 100;
        double intensity = Math.min(1.0, energy / (double) maxEnergy);
        int gray = (int) (211 - 211 * (1.0 - intensity));
        return String.format("rgb(%d, %d, %d)", gray, gray, gray);
    }
    @FXML
    public void generateReport() {
        AbstractWorldMap map = simulation.getSimConfig().currentMap();

        String mostFrequentGenome = map.getGenotypes().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");

        double averageEnergyForLiveAnimals = map.getAnimals().stream()
                .filter(Animal::isAlive)
                .mapToInt(Animal::getEnergy)
                .average()
                .orElse(0.0);

        double averageDaysAliveForLiveAnimals = map.getAnimals().stream()
                .filter(Animal::isAlive)
                .mapToInt(Animal::getAge)
                .average()
                .orElse(0.0);

        double averageChildrenForLiveAnimals = map.getAnimals().stream()
                .filter(Animal::isAlive)
                .mapToInt(Animal::getNumberOfChildren)
                .average()
                .orElse(0.0);

        int freeFields = map.getWidth() * map.getHeight() - map.getOccupiedFields().size();

        String report = String.format(
                """
                Liczba zwierząt na mapie: %d
                Liczba roślin na mapie: %d
                Liczba wolnych pól: %d
                Najpopularniejszy genotyp: %s
                Średni poziom energii dla żyjących zwierząt: %.2f
                Średnia długość życia żyjących zwierząt: %.2f
                Średnia liczba dzieci dla żyjących zwierząt: %.2f
                Maksymalna liczba zwierząt na mapie: %d
                """,
                map.getAnimals().size(),
                map.getPlants().size(),
                freeFields,
                mostFrequentGenome,
                averageEnergyForLiveAnimals,
                averageDaysAliveForLiveAnimals,
                averageChildrenForLiveAnimals,
                map.getMaxAnimalSize()
        );

        reportTextArea.setText(report);
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