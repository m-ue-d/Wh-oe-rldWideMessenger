package spg.client.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Current {
    public static final SimpleStringProperty panel = new SimpleStringProperty(
        Settings.mainTitle.get()
    );

    public static final SimpleIntegerProperty server = new SimpleIntegerProperty(
        -1
    );

    public static final SimpleIntegerProperty channel = new SimpleIntegerProperty(
        -1
    );
}
