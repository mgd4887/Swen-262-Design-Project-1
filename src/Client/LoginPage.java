package Client;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import system.SerializedLibraryBookManagementSystem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPage extends Page {
    private final int clientID;

    public LoginPage(ClientApplication clientApplication, SerializedLibraryBookManagementSystem lbms, int clientID) {
        super(clientApplication, lbms);
        this.clientID = clientID;
    }

    @Override
    public Node getRoot() {
        HBox root = new HBox();

        HBox username = new HBox();
        TextField usernameField = new TextField();
        Label usernameLabel = new Label("Username: ");
        username.getChildren().addAll(usernameLabel,usernameField);

        HBox password = new HBox();
        TextField passwordField = new TextField();
        Label passwordLabel = new Label("Password: ");
        password.getChildren().addAll(passwordLabel, passwordField);

        HBox submit = new HBox();
        Button submitButton = new Button("login");
        submitButton.setOnMouseClicked(event -> submitLogin(usernameField.getCharacters(),
                passwordField.getCharacters(),
                clientID));
        submit.getChildren().add(submitButton);

        root.getChildren().addAll(username,password, submit);

        return root;
    }

    private void submitLogin(CharSequence username, CharSequence password, int clientID) {
        String request = clientID + ",login," + username + "," + password + ";";
        String response = LBMS.performRequest(request);
        String regex = clientID + ",login,success;";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(response);
        if (matcher.matches()){
            clientApplication.getCurrentClient().setName(username.toString());
            clientApplication.changePage(new MenuPage(clientApplication,LBMS));
        }else{
            clientApplication.addError(response);
        }
    }
}
