package agh.ics.oop.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import agh.ics.oop.*;
import agh.ics.oop.model.*;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;


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
            SimulationConfig config = createSimulationConfig();
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

    @FXML
    public void onSaveConfigClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Configuration");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                // Tworzenie obiektu SimulationConfig
                SimulationConfig config = createSimulationConfig();

                // Serializacja do pliku JSON
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, config);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void onLoadConfigClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Configuration");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                // Deserializacja pliku JSON do SimulationConfig
                ObjectMapper objectMapper = new ObjectMapper();
                SimulationConfig config = objectMapper.readValue(file, SimulationConfig.class);

                // Wypełnienie UI wczytaną konfiguracją
                applyConfigToUI(config);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private SimulationConfig createSimulationConfig() {
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

        GrowthVariant growthVariant = GrowthVariantFactory.create(growthVariantComboBox.getValue(), width, height);
        MapVariant mapVariant = MapVariantFactory.create(mapVariantComboBox.getValue(), width, height, growthVariant, animalConfig);

        return new SimulationConfig(map, mapVariant, growthVariant, initialAnimals, simulationSpeed, animalConfig);
    }
    private void applyConfigToUI(SimulationConfig config) {
        AbstractWorldMap map = config.currentMap();
        AnimalConfig animalConfig = config.animalConfig();

        widthInput.setText(String.valueOf(map.getWidth()));
        heightInput.setText(String.valueOf(map.getHeight()));
        plantEnergyInput.setText(String.valueOf(map.getPlantEnergy()));
        startingEnergyInput.setText(String.valueOf(animalConfig.initialEnergy()));
        genomeLengthInput.setText(String.valueOf(animalConfig.genomeLength()));
        reproductionEnergyInput.setText(String.valueOf(animalConfig.reproductionEnergy()));
        birthEnergyInput.setText(String.valueOf(animalConfig.birthEnergy()));
        minMutationsInput.setText(String.valueOf(animalConfig.minMutation()));
        maxMutationsInput.setText(String.valueOf(animalConfig.maxMutation()));
        initialAnimalsInput.setText(String.valueOf(config.animalCount()));
        simulationSpeedInput.setText(String.valueOf(config.simulationSpeed()));
        growthVariantComboBox.setValue(config.growthVariant().toString());
        moveVariantComboBox.setValue(animalConfig.moveVariant().toString());
        mapVariantComboBox.setValue(config.mapVariant().toString());
    }




    private int parseInput(TextField input) {
        return Integer.parseInt(input.getText());
    }
}