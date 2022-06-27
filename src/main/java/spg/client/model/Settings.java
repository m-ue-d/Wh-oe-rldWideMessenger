package spg.client.model;

import javafx.animation.Interpolator;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;
import spg.client.view.utility.Interpolators;

public class Settings {
    public static void readSettings() {
        // read settings from file
    }

    public static final String QUOTE = "The messenger with revolutionary technology";
    public static final SimpleStringProperty title = new SimpleStringProperty(
        "Whörld Wide Messenger"
    );
    public static final SimpleStringProperty version = new SimpleStringProperty(
        "0.1.0"
    );
    public static final SimpleObjectProperty<Color> bgPrimary = new SimpleObjectProperty<>(
        Color.web("#1d1d21")
    );
    public static final SimpleObjectProperty<Color> bgSecondary = new SimpleObjectProperty<>(
        Color.web("#26262b")
    );
    public static final SimpleObjectProperty<Color> bgTertiary = new SimpleObjectProperty<>(
        Color.web("#61636a")
    );
    public static final SimpleObjectProperty<Color> colorAccent = new SimpleObjectProperty<>(
        Color.web("#0074f1")
    );
    public static final SimpleObjectProperty<Color> fontMain = new SimpleObjectProperty<>(
        Color.web("#ebf2ff")
    );
    public static final SimpleObjectProperty<Interpolator> easeInOutBack = new SimpleObjectProperty<>(
        Interpolators.Companion.getEaseInOutBack()
    );
    public static final SimpleIntegerProperty account = new SimpleIntegerProperty(
        -1
    );
}
