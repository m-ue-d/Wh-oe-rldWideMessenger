package spg.client.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public final class Internal {
    public static final SimpleStringProperty version = new SimpleStringProperty(
        "0.1.0"
    );

    public static final SimpleStringProperty mainTitle = new SimpleStringProperty(
        "Whörld Wide Messenger"
    );

    public static final SimpleStringProperty welcomeTitle = new SimpleStringProperty(
        "Welcome"
    );

    public static final SimpleStringProperty titleQuote = new SimpleStringProperty(
        "The messenger with revolutionary technology"
    );

    public static final ObservableList<String> settingGroups = FXCollections.observableList(
        List.of("Account", "Appearance", "Network")
    );
}
