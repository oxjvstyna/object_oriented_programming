package agh.ics.oop.presenter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import agh.ics.oop.*;
import agh.ics.oop.model.*;

public class StartPresenter {
    @FXML
    private TextField widthInput, heightInput, plantEnergyInput, startingEnergyInput, genomeLengthInput,
            reproductionEnergyInput, birthEnergyInput, minMutationsInput, maxMutationsInput,
            initialAnimalsInput, simulationSpeedInput;
    @FXML
    private ComboBox<String> growthVariantComboBox, moveVariantComboBox, mapVariantComboBox, mutationVariantComboBox;

    @FXML
    private void initialize() {
        growthVariantComboBox.getItems().addAll("FertileEquator");
        moveVariantComboBox.getItems().addAll("SlightMadness", "TotalPredestination");
        mapVariantComboBox.getItems().addAll("GlobeMap", "OwlbearMap");
        mutationVariantComboBox.getItems().addAll("FullRandomness");

        growthVariantComboBox.setValue("FertileEquator");
        moveVariantComboBox.setValue("SlightMadness");
        mapVariantComboBox.setValue("GlobeMap");
        mutationVariantComboBox.setValue("FullRandomness");
    }

    @FXML
    private void onSimulationStartClicked() {
        try {
            int width = parseInput(widthInput);
            int height = parseInput(heightInput);
            int plantEnergy = parseInput(plantEnergyInput);
            int startingEnergy = parseInput(startingEnergyInput);
            int genomeLength = parseInput(genomeLengthInput);
            int reproductionEnergy = parseInput(reproductionEnergyInput);
            int birthEnergy = parseInput(birthEnergyInput);
            int minMutations = parseInput(minMutationsInput);
            int maxMutations = parseInput(maxMutationsInput);
            int initialAnimals = parseInput(initialAnimalsInput);
            int simulationSpeed = parseInput(simulationSpeedInput);

            AnimalConfig animalConfig = new AnimalConfig(
                    startingEnergy, genomeLength, reproductionEnergy, birthEnergy, minMutations, maxMutations,
                    MoveVariantFactory.create(moveVariantComboBox.getValue())
            );
            AbstractWorldMap map = MapFactory.create(mapVariantComboBox.getValue(), width, height, growthVariantComboBox.getValue(), animalConfig);
            map.setPlantEnergy(plantEnergy);

            SimulationConfig config = new SimulationConfig(map, GrowthVariantFactory.create(growthVariantComboBox.getValue(), width, height), initialAnimals, simulationSpeed, MoveVariantFactory.create(moveVariantComboBox.getValue()));
            SimulationEngine engine = new SimulationEngine(new Simulation(config));

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("simulation.fxml"));
            Stage simulationStage = new Stage();
            simulationStage.setTitle("Simulation");
            simulationStage.setScene(new Scene(loader.load()));
            simulationStage.show();

            SimulationPresenter simulationPresenter = loader.getController();
            simulationPresenter.initializeSimulation(engine);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int parseInput(TextField input) {
        return Integer.parseInt(input.getText());
    }
}