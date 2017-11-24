package Arena.Client.Games;

import java.nio.file.Path;

public class GameDescription {
    public int id;
    public String name;
    public String description;
    public boolean approved;
    public Path location;

    public GameDescription(int id, String name, String description, boolean approved) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.approved = approved;
    }

    public GameDescription(String name, Path location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getApproved() {
        return approved;
    }

    @Override
    public String toString() {
        return name;
    }
}
