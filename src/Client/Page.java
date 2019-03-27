package Client;

import javafx.scene.Scene;

public abstract class Page {
    Client client;

    public Page(Client client) {
        this.client = client;
    }

    public abstract Scene getScene();

}
