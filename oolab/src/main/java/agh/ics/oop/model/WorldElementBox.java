package agh.ics.oop.model;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class WorldElementBox extends VBox {
    public WorldElementBox(WorldElement element) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + element.getTextureResource())));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(imageView);
    }
}
