package Client;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import system.SerializedLibraryBookManagementSystem;

public class StartPage extends Page {

    public StartPage(ClientApplication clientApplication, SerializedLibraryBookManagementSystem lbms) {
        super(clientApplication, lbms);
    }

    @Override
    public Node getRoot() {
        BorderPane mainBorderPane = new BorderPane();
        Label lbmstitle = new Label("Library Book Management System");

        mainBorderPane.setCenter(lbmstitle);

        return mainBorderPane;
    }
}
