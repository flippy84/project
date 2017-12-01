package Arena.Shared;

import javafx.scene.image.ImageView;

public class Advertisement {
    public int id;
    public String name;
    public int duration;
    public String image;
    public String url;

    public Advertisement(int id, String name, int duration, String image, String url) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.image = image;
        this.url = url;
    }


}
