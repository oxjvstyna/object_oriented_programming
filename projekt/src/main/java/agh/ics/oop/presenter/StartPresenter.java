package agh.ics.oop.presenter;
import java.io.*;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import agh.ics.oop.*;
import agh.ics.oop.model.*;

import java.io.IOException;


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
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Properties Files", "*.properties"));
    File file = fileChooser.showSaveDialog(null);

    if (file != null) {
        try {
            SimulationConfig config = createSimulationConfig();
            saveConfigToProperties(config, file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    @FXML
    public void onLoadConfigClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Configuration");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Properties Files", "*.properties"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                SimulationConfig config = loadConfigFromProperties(file.getAbsolutePath());
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
        GrowthVariant growthVariant = GrowthVariantFactory.create(growthVariantComboBox.getValue(), width, height);

        MapVariant mapVariant = MapVariantFactory.create(mapVariantComboBox.getValue(), width, height, growthVariant, animalConfig);

        AbstractWorldMap map = MapFactory.create(mapVariant, width, height, growthVariant, animalConfig);
        map.setPlantEnergy(plantEnergy);


        return new SimulationConfig(map, mapVariant, plantEnergy, growthVariant, initialAnimals, simulationSpeed, animalConfig);
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

    public void saveConfigToProperties(SimulationConfig config, String filePath) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("width", String.valueOf(config.currentMap().getWidth()));
        properties.setProperty("height", String.valueOf(config.currentMap().getHeight()));
        properties.setProperty("plantEnergy", String.valueOf(config.currentMap().getPlantEnergy()));
        properties.setProperty("startingEnergy", String.valueOf(config.animalConfig().initialEnergy()));
        properties.setProperty("genomeLength", String.valueOf(config.animalConfig().genomeLength()));
        properties.setProperty("simulationSpeed", String.valueOf(config.simulationSpeed()));
        properties.setProperty("mapVariant", config.mapVariant().toString());
        properties.setProperty("growthVariant", config.growthVariant().toString());
        properties.setProperty("moveVariant", config.animalConfig().moveVariant().toString());
        properties.setProperty("reproductionEnergy", String.valueOf(config.animalConfig().reproductionEnergy()));
        properties.setProperty("birthEnergy", String.valueOf(config.animalConfig().birthEnergy()));
        properties.setProperty("minMutations", String.valueOf(config.animalConfig().minMutation()));
        properties.setProperty("maxMutations", String.valueOf(config.animalConfig().maxMutation()));
        properties.setProperty("initialAnimals", String.valueOf(config.animalCount()));

        try (FileOutputStream out = new FileOutputStream(filePath)) {
            properties.store(out, "Simulation Configuration");
        }
    }
    public SimulationConfig loadConfigFromProperties(String filePath) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream(filePath)) {
            properties.load(in);
        }

        int width = Integer.parseInt(properties.getProperty("width"));
        int height = Integer.parseInt(properties.getProperty("height"));
        int plantEnergy = Integer.parseInt(properties.getProperty("plantEnergy"));
        int startingEnergy = Integer.parseInt(properties.getProperty("startingEnergy"));
        int genomeLength = Integer.parseInt(properties.getProperty("genomeLength"));
        int reproductionEnergy = Integer.parseInt(properties.getProperty("reproductionEnergy"));
        int birthEnergy = Integer.parseInt(properties.getProperty("birthEnergy"));
        int minMutations = Integer.parseInt(properties.getProperty("minMutations"));
        int maxMutations = Integer.parseInt(properties.getProperty("maxMutations"));
        int initialAnimals = Integer.parseInt(properties.getProperty("initialAnimals"));
        int simulationSpeed = Integer.parseInt(properties.getProperty("simulationSpeed"));


        MoveVariant moveVariant = MoveVariantFactory.create(properties.getProperty("moveVariant"));
        GrowthVariant growthVariant = GrowthVariantFactory.create(properties.getProperty("growthVariant"), width, height);
        AnimalConfig animalConfig = new AnimalConfig(startingEnergy, genomeLength, reproductionEnergy, birthEnergy, minMutations, maxMutations, moveVariant);
        MapVariant mapVariant = MapVariantFactory.create(properties.getProperty("mapVariant"), width, height, growthVariant, animalConfig);
        AbstractWorldMap map = MapFactory.create(mapVariant, width, height, growthVariant, animalConfig);
        return new SimulationConfig(map, mapVariant, plantEnergy, growthVariant, initialAnimals, simulationSpeed, animalConfig);
    }

    private int parseInput(TextField input) {
        return Integer.parseInt(input.getText());
    }

}