package Arena.Client.Games;

import javafx.beans.property.SimpleStringProperty;

public class GameDescription {
    public int id;
    public String name;
    public String description;
    public boolean approved;

    public GameDescription(int id, String name, String description, boolean approved) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.approved = approved;
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
}
