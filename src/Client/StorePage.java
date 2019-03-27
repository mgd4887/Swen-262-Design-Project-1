package Client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class StorePage extends Page {
    public StorePage(Client client) {
        super(client);
    }

    @Override
    public Scene getScene() {

        //create a return to home button
        HBox returnBox = new HBox();
        Button returnButton = new Button("Return to menu");
        returnButton.setOnMouseClicked(mouseEvent -> client.changePage(new MenuPage(client)));
        returnBox.getChildren().addAll(returnButton);
        returnBox.setSpacing(10);
        returnBox.setPrefWidth(250);

        BorderPane root = new BorderPane();
        root.setBottom(returnBox);
        return new Scene(root);
    }
}
