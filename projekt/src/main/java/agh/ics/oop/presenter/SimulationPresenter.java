package agh.ics.oop.presenter;

import agh.ics.oop.*;
import agh.ics.oop.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SimulationPresenter {

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
    public void initialize() {
        growthVariantComboBox.getItems().addAll("FertileEquator", "OtherGrowthVariant");
        moveVariantComboBox.getItems().addAll("TotalPredestination", "OtherMoveVariant");
        mapVariantComboBox.getItems().addAll("Globe", "OtherMapVariant");
        mutationVariantComboBox.getItems().addAll("Random", "OtherMutationVariant");
    }

    @FXML
    private void onSimulationStartClicked() {
        String growthVariant = growthVariantComboBox.getValue();
        String moveVariant = moveVariantComboBox.getValue();
        String mapVariant = mapVariantComboBox.getValue();
        String mutationVariant = mutationVariantComboBox.getValue();
        int width = Integer.parseInt(widthInput.getText());
        int height = Integer.parseInt(heightInput.getText());
        int plantEnergy = Integer.parseInt(plantEnergyInput.getText());

        GrowthVariant selectedGrowthVariant = growthVariant.equals("FertileEquator") ? new FertileEquator(width, height) : null;
        MoveVariant selectedMoveVariant = moveVariant.equals("TotalPredestination") ? new TotalPredestination() : null;
        AbstractWorldMap map = new GlobeMap(width, height, selectedGrowthVariant, selectedMoveVariant);
        MutationVariant selectedMutationVariant = mutationVariant.equals("Random") ? new FullRandomness() : null;
        map.setPlantEnergy(plantEnergy);

        SimulationConfig config = new SimulationConfig(map, selectedGrowthVariant, 5000, 100, selectedMoveVariant);
        SimulationEngine engine = new SimulationEngine(new Simulation(config));
        engine.runAsyncInThreadPool();
    }
}