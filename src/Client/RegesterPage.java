package Client;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import system.SerializedLibraryBookManagementSystem;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegesterPage extends Page {
    private final int clientID;

    public RegesterPage(ClientApplication clientApplication, SerializedLibraryBookManagementSystem LBMS, int clientID) {
        super(clientApplication, LBMS);
        this.clientID = clientID;
    }

    @Override
    public Node getRoot() {
        GridPane root = new GridPane();

        HBox firstName = new HBox();
        TextField nameField = new TextField();
        Label nameLabel = new Label("First name: ");
        firstName.getChildren().addAll(nameLabel,nameField);

        HBox lastName = new HBox();
        TextField lastNameField = new TextField();
        Label lastNameLabel = new Label("Last name: ");
        lastName.getChildren().addAll(lastNameLabel, lastNameField);

        HBox address = new HBox();
        TextField addressField = new TextField();
        Label addressLabel = new Label("Address: ");
        address.getChildren().addAll(addressLabel, addressField);

        HBox phoneNumber = new HBox();
        TextField phoneField = new TextField();
        Label phoneLabel = new Label("Phone number: ");
        phoneNumber.getChildren().addAll(phoneLabel, phoneField);

        HBox submit = new HBox();
        Button submitButton = new Button("Register");
        submitButton.setOnMouseClicked(event -> submitRegester(nameField.getCharacters(),
                                                                lastNameField.getCharacters(),
                                                                addressField.getCharacters(),
                                                                phoneField.getCharacters(),
                                                                clientID));
        submit.getChildren().add(submitButton);

        root.addColumn(0, firstName,lastName,address,phoneNumber,submit);

        return root;
    }

    private void submitRegester(CharSequence firstNameCharacters, CharSequence lastNameFieldCharacters, CharSequence addressFieldCharacters, CharSequence phoneFieldCharacters, int clientID) {
        String request = clientID + ",register," + firstNameCharacters.toString() + "," + lastNameFieldCharacters.toString() + "," + addressFieldCharacters.toString() + "," + phoneFieldCharacters.toString() + ";";
        String response = LBMS.performRequest(request);
        // should be in the format "register,visitor ID,registration date;"
        String regex = "(.+),([0-9]{10}),(.+);";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(response);
        if (matcher.matches()){
            int id = Integer.parseInt(matcher.group(2));
            clientApplication.getCurrentClient().setID(id);
        }else{
            clientApplication.addError(response);
        }
    }
}
