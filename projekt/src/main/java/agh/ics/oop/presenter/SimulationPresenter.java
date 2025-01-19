package agh.ics.oop.presenter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import agh.ics.oop.*;
import agh.ics.oop.model.*;

import java.util.UUID;

public class SimulationPresenter {


    @FXML
    private TextField animalIdField;

    private AnimalTracker animalTracker = new AnimalTracker();

    @FXML
    private GridPane mapGrid;
    @FXML
    private Label followedAnimalEnergyLabel;
    @FXML
    private Label followedAnimalBirthDayLabel;
    @FXML
    private Label followedAnimalDeathDayLabel;
    @FXML
    private Label followedAnimalChildrenCountLabel;
    @FXML
    private Label followedAnimalDescendantsCountLabel;
    @FXML
    private Label followedAnimalGenotypeLabel;
    @FXML
    private Label followedAnimalActiveGeneIndexLabel;
    @FXML
    private Label followedAnimalPlantsEatenLabel;

    @FXML
    private TextArea animalStatusTextArea;

    private AnimalTracker tracker;

    public void setAnimalTracker(AnimalTracker tracker) {
        this.tracker = tracker;
    }


    private Animal followedAnimal = null;
    private boolean isRunning = false;
    private Timeline timeline;
    private Simulation simulation;
    private int currentDay = 0;

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
            updateAnimalStatus(currentDay);
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
    public void updateAnimalStatus(int currentDay) {
        if (tracker != null && tracker.isTracking() && followedAnimal != null) {
            // Zbieramy dane do raportu o śledzonym zwierzęciu
            String status = String.format(
                    """
                    Animal ID: %d
                    Genome: %s
                    Active Gene: %d
                    Energy: %d
                    Plants Eaten: %d
                    Children Count: %d
                    Descendants Count: %d
                    Age: %d
                    Death Day: %s
                    """,
                    followedAnimal.getId(),  // ID zwierzęcia
                    followedAnimal.getGenome(),  // Genotyp zwierzęcia
                    followedAnimal.getGenome().getGenes().get(followedAnimal.getMoveIndex()),  // Aktywny gen
                    followedAnimal.getEnergy(),  // Energia zwierzęcia
//                    plantsEaten,  // Liczba zjedzonych roślin
                    followedAnimal.getNumberOfChildren(),  // Liczba dzieci
//                    descendantsCount,  // Liczba potomków
                    followedAnimal.isAlive() ? currentDay - followedAnimal.getAge() : -1  // Wiek zwierzęcia
//                    deathDay == -1 ? "Still alive" : deathDay  // Dzień śmierci (jeśli żyje, to "Still alive")
            );


            // Ustawienie zaktualizowanego statusu w polu tekstowym
            animalStatusTextArea.setText(status);

            // Zaktualizowanie informacji na etykietach
            followedAnimalEnergyLabel.setText(String.valueOf(followedAnimal.getEnergy()));  // Energia
            followedAnimalChildrenCountLabel.setText(String.valueOf(followedAnimal.getNumberOfChildren()));  // Liczba dzieci
            followedAnimalGenotypeLabel.setText(followedAnimal.getGenome().toString());  // Genotyp
            followedAnimalActiveGeneIndexLabel.setText(String.valueOf(followedAnimal.getMoveIndex()));  // Indeks aktywnego genu
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
        int maxEnergy = 100;
        double intensity = Math.min(1.0, energy / (double) maxEnergy);

        // Gradient od jasnoszarego (wysoka energia) do czarnego (niska energia)
        int gray = (int) (211 - 211 * (1.0 - intensity)); // Energia 0 -> 0 (czarny), energia max -> 211 (jasnoszary)
        return String.format("rgb(%d, %d, %d)", gray, gray, gray);
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
                animalTracker.startTracking(animal);
                System.out.println("Started tracking animal with ID: " + animalId);

                // Zaktualizuj status po rozpoczęciu śledzenia
                updateAnimalStatus(currentDay);
            } else {
                System.out.println("No animal found with ID: " + animalId);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid ID format. Please enter a valid ID.");
        }
    }


}