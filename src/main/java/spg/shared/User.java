package spg.shared;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private final int id;
    private final String uname;
    private final String img;
    private final LocalDateTime since;

    public User(int id, String uname, LocalDateTime since) {
        this.id = id;
        this.uname = uname;
        this.img = "/spg/server/database/avatars/" + id + ".png";
        this.since = since;
    }

    public int getId() {
        return id;
    }

    public String getUname() {
        return uname;
    }

    public String getImg() {
        return img;
    }

    public LocalDateTime getSince() {
        return since;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", uname='" + uname + '\'' +
            ", img='" + img + '\'' +
            ", since=" + since.format(DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss"
            )) + '\'' +
        '}';
    }
}
