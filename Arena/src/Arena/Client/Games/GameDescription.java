package Arena.Client.Games;

import javafx.beans.property.SimpleStringProperty;

public class GameDescription {
    public int id;
    public String name;
    public String description;

    public GameDescription(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
