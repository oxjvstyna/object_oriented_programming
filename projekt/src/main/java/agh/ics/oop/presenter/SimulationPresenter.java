package agh.ics.oop.presenter;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import agh.ics.oop.*;
import agh.ics.oop.model.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class SimulationPresenter {

    public Label descriptionLabel;
    @FXML
    private ComboBox<String> growthVariantComboBox;
    @FXML
    private ComboBox<String> moveVariantComboBox;
    @FXML
    private ComboBox<String> mapVariantComboBox;
    @FXML
    private ComboBox<String> mutationVariantComboBox;
    @FXML
    private TextField widthInput;
    @FXML
    private TextField heightInput;
    @FXML
    private TextField plantEnergyInput;

    @FXML
    private GridPane mapGrid;

    private Simulation simulation;


    @FXML
    private void onSimulationStartClicked() {
            try {
                // Validate ComboBox values
//                if (growthVariantComboBox.getValue() == null ||
//                        moveVariantComboBox.getValue() == null ||
//                        mapVariantComboBox.getValue() == null ||
//                        mutationVariantComboBox.getValue() == null) {
//                    System.out.println("All options must be selected.");
//                    return;
//                }

                // Parse input fields
                int width = Integer.parseInt(widthInput.getText());
                int height = Integer.parseInt(heightInput.getText());
                int plantEnergy = Integer.parseInt(plantEnergyInput.getText());

                // Create simulation components
//                GrowthVariant selectedGrowthVariant = growthVariantComboBox.getValue().equals("FertileEquator") ? new FertileEquator(width, height) : null;
//                MoveVariant selectedMoveVariant = moveVariantComboBox.getValue().equals("TotalPredestination") ? new TotalPredestination() : null;
//                MutationVariant selectedMutationVariant = mutationVariantComboBox.getValue().equals("Random") ? new FullRandomness() : null;

                AnimalConfig animalConfig = new AnimalConfig(10, 10, 10, 10, 10, 10, new TotalPredestination());
                AbstractWorldMap map = new GlobeMap(width, height, new FertileEquator(width, height), animalConfig);
                map.setPlantEnergy(plantEnergy);

                SimulationConfig config = new SimulationConfig(map, new FertileEquator(width, height), 5000, 100, new TotalPredestination());
                SimulationEngine engine = new SimulationEngine(new Simulation(config));

                // Load the simulation window
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("simulation.fxml"));
                BorderPane simulationRoot = loader.load();
                Stage simulationStage = new Stage();
                simulationStage.setTitle("Simulation");
                simulationStage.setScene(new Scene(simulationRoot));
                simulationStage.show();

                // Initialize the simulation controller
                SimulationPresenter simulationPresenter = loader.getController();
                simulationPresenter.initializeSimulation(engine);


            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numeric values for width, height, and plant energy.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to load the simulation UI: " + e.getMessage());
            }
    }

    public void initializeSimulation(SimulationEngine engine) {
        this.simulation = engine.getSimulation();
        startSimulation();

    }

    private void initializeGrid(AbstractWorldMap map) {
        mapGrid.getChildren().clear();
        mapGrid.setGridLinesVisible(true);

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Label cell = new Label();
                cell.setStyle("-fx-font-size: 14; -fx-alignment: center; -fx-border-color: black;");
                mapGrid.add(cell, x, y);
            }
        }
    }


    private void startSimulation() {
        this.simulation.run();
        initializeGrid(simulation.getSimConfig().currentMap());
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> {
                    simulation.runStep();
                    renderMap();
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    private void renderMap() {
        mapGrid.getChildren().clear(); // Clear grid contents

        AbstractWorldMap map = simulation.getSimConfig().currentMap();

        // Get grid dimensions
        int gridWidth = map.getWidth();
        int gridHeight = map.getHeight();

        // Get available space for the map
        double availableWidth = mapGrid.getScene().getWidth();
        double availableHeight = mapGrid.getScene().getHeight();

        // Calculate cell dimensions
        double cellWidth = availableWidth / gridWidth;
        double cellHeight = availableHeight / gridHeight;
        double cellSize = Math.min(cellWidth, cellHeight); // Use the smaller dimension to ensure square cells

        // Scale font size relative to cell size
        double fontSize = cellSize * 0.4; // Adjust multiplier as needed for readability

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                String cellContent = map.getCellContent(x, y); // Get cell content

                // Create a cell
                Label cell = new Label(cellContent);
                cell.setPrefWidth(cellSize);  // Set constant width
                cell.setPrefHeight(cellSize); // Set constant height
                cell.setStyle("-fx-font-size: " + fontSize + "px; -fx-alignment: center; -fx-border-color: black;");

                // Add cell to the grid
                mapGrid.add(cell, x, y);
            }
        }
    }




}
