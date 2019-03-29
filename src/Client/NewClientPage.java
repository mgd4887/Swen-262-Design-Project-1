package Client;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import system.SerializedLibraryBookManagementSystem;

public class NewClientPage extends Page{
    private final int clientID;

    public NewClientPage(ClientApplication clientApplication, SerializedLibraryBookManagementSystem LBMS, int clientID) {
        super(clientApplication, LBMS);
        this.clientID = clientID;
    }

    @Override
    public Node getRoot() {
        GridPane root = new GridPane();
        HBox register = new HBox();
        Button registerButton = new Button("Regester new visitor");
        registerButton.setOnMouseClicked(event -> clientApplication.changePage(new RegesterPage(clientApplication, LBMS, clientID)));
        register.getChildren().add(registerButton);

        HBox login = new HBox();
        Button loginButton = new Button("login");
        loginButton.setOnMouseClicked(event -> clientApplication.changePage(new LoginPage(clientApplication, LBMS, clientID)));
        login.getChildren().add(loginButton);

        root.addColumn(0,login,register);
        return root;
    }
}
