package spg.client.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public final class ServerListItem {
    private final SimpleStringProperty nameProperty;
    private final SimpleStringProperty ipProperty;
    private final SimpleIntegerProperty portProperty;

    public ServerListItem(String name, String ip, int port) {
        this.nameProperty = new SimpleStringProperty(name);
        this.ipProperty = new SimpleStringProperty(ip);
        this.portProperty = new SimpleIntegerProperty(port);
    }

    public SimpleStringProperty getNameProperty() {
        return this.nameProperty;
    }

    public SimpleStringProperty getIpProperty() {
        return this.ipProperty;
    }

    public SimpleIntegerProperty getPortProperty() {
        return this.portProperty;
    }

    public String getName() {
        return this.nameProperty.get();
    }

    public String getIp() {
        return this.ipProperty.get();
    }

    public int getPort() {
        return this.portProperty.get();
    }
}