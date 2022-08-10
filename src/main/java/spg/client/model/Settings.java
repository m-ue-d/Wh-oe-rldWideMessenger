package spg.client.model;

import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import spg.shared.User;

import java.util.ArrayList;

public final class Settings {
    /**
     * The user account that is used to log in on startup
     */
    public static final SimpleObjectProperty<User> account = new SimpleObjectProperty<>(
        null
    );

    /**
     * A list of registered servers on this account. Updates to this list will be reflected in the listview.
     */
    public static final ObservableList<ServerListItem> servers = FXCollections.observableList(
        new ArrayList<>(), param -> new Observable[] {
            param.getNameProperty(), param.getIpProperty(), param.getPortProperty()
        }
    );

    /**
     * The primary color of the application. It is the darkest color of all.
     */
    public static final SimpleObjectProperty<Color> bgPrimary = new SimpleObjectProperty<>(
        Color.web("#1d1d21")
    );

    /**
     * A slightly lighter variant of the primary color. Used for elevated elements.
     */
    public static final SimpleObjectProperty<Color> bgSecondary = new SimpleObjectProperty<>(
        Color.web("#26262b")
    );

    /**
     * The lightest color in the application. Used for backgrounds and light fonts.
     */
    public static final SimpleObjectProperty<Color> bgTertiary = new SimpleObjectProperty<>(
        Color.web("#61636a")
    );

    /**
     * A colorful alternative to the other colors. Mainly used for tab indicators.
     */
    public static final SimpleObjectProperty<Color> colorAccent = new SimpleObjectProperty<>(
        Color.web("#0074f1")
    );

    /**
     * The main color of the application's font. Usually set to a color close to white.
     */
    public static final SimpleObjectProperty<Color> fontMain = new SimpleObjectProperty<>(
        Color.web("#ebf2ff")
    );
}
