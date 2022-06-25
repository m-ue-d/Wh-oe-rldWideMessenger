package spg.client.model;

import javafx.scene.image.Image;

public class User {
    private final String username;
    private final Image avatar;
    // other data like friend list, etc.

    public User(String username, Image avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public Image getAvatar() {
        return avatar;
    }
}
