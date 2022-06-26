package spg.client.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

public class Settings {
    public static final String QUOTE = "The messenger with revolutionary technology";
    public static SimpleStringProperty title = new SimpleStringProperty(
        "Whörld Wide Messenger"
    );
    public static SimpleStringProperty version = new SimpleStringProperty(
        "0.1.0"
    );
    public static SimpleObjectProperty<Color> bgMain = new SimpleObjectProperty<>(
        Color.web("#1e1d2a")
    );
    public static SimpleObjectProperty<Color> bgSecondary = new SimpleObjectProperty<>(
        Color.web("#272836")
    );
    public static SimpleObjectProperty<Color> fontMain = new SimpleObjectProperty<>(
        Color.web("#ebf2ff")
    );
}
