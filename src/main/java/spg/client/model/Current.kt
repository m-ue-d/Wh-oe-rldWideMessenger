package spg.client.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Current {
    public static final SimpleStringProperty panel = new SimpleStringProperty(
        Internal.mainTitle.get()
    );

    public static final SimpleObjectProperty<ServerListItem> server = new SimpleObjectProperty<>(
        null
    );

    public static final SimpleIntegerProperty channel = new SimpleIntegerProperty(
        -1
    );
}
