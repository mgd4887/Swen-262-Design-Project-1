package Client;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import system.SerializedLibraryBookManagementSystem;


public class CreateAccountPage extends Page {
    /**
     * constructor for all pages
     * creates a page
     *
     * @param clientApplication the client that this page is in
     * @param LBMS              the LBMS the client is connected to
     */
    public CreateAccountPage(ClientApplication clientApplication, SerializedLibraryBookManagementSystem LBMS) {
        super(clientApplication, LBMS);
    }

    @Override
    public Node getRoot() {
        //client ID,create,username,password,role,visitor ID;

        int clientID = clientApplication.getCurrentClient().getID();

        HBox username = new HBox();
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        username.getChildren().addAll(usernameLabel, usernameField);

        HBox password = new HBox();
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        password.getChildren().addAll(passwordLabel,passwordField);


        


        return null;
    }
}
