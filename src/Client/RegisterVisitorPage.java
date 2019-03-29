package Client;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import system.SerializedLibraryBookManagementSystem;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterVisitorPage extends Page {

    /**
     * constructor for all pages
     * creates a page
     *
     * @param clientApplication the client that this page is in
     * @param LBMS              the LBMS the client is connected to
     */
    public RegisterVisitorPage(ClientApplication clientApplication, SerializedLibraryBookManagementSystem LBMS) {
        super(clientApplication, LBMS);
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
        submitButton.setOnMouseClicked(event -> submitRegister(nameField.getCharacters(),
                                                                lastNameField.getCharacters(),
                                                                addressField.getCharacters(),
                                                                phoneField.getCharacters(),
                                                                clientApplication.currentClientID()));
        submit.getChildren().add(submitButton);

        //create a return to home button
        HBox returnBox = new HBox();
        Button returnButton = new Button("Return to menu");
        returnButton.setOnMouseClicked(mouseEvent -> clientApplication.changePage(new MenuPage(clientApplication, LBMS)));
        returnBox.getChildren().addAll(returnButton);
        returnBox.setSpacing(10);
        returnBox.setPrefWidth(250);

        root.addColumn(0, firstName,lastName,address,phoneNumber,submit, returnBox);

        return root;
    }


    private void submitRegister(CharSequence firstNameCharacters, CharSequence lastNameFieldCharacters, CharSequence addressFieldCharacters, CharSequence phoneFieldCharacters, int clientID) {
        String request = clientID + ",register," + firstNameCharacters.toString() + "," + lastNameFieldCharacters.toString() + "," + addressFieldCharacters.toString() + "," + phoneFieldCharacters.toString() + ";";
        String response = LBMS.performRequest(request);
        // should be in the format "register,visitor ID,registration date;"
        String regex = "(.+),([0-9]{10}),(.+);";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(response);
        if (matcher.matches()){
            int id = Integer.parseInt(matcher.group(2));
            clientApplication.changePage(new CreateAccountPage(clientApplication, LBMS, id));
        }else{
            clientApplication.addError(response);
        }
    }
}
