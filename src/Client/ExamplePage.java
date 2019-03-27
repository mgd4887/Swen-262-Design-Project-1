package Client;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ExamplePage extends Page {
    public ExamplePage(Client client) {
        super(client);
    }

    @Override
    public Scene getScene() {

        //create outer boarder pane
        BorderPane outerPane = new BorderPane();
        Text menuText = new Text("Welcome\r\nPick an option");
        menuText.setFont(Font.font("Verdana", 20));
        outerPane.setTop(menuText);
        GridPane centerPane = new GridPane();
        outerPane.setCenter(centerPane);
        HBox textBox = new HBox();
        TextField textField = new TextField ();
        Label textLabel = new Label("Text:");
        Button testButton = new Button("Submit");
        testButton.setDefaultButton(true);
        testButton.setOnMouseClicked(mouseEvent -> this.submit(textField.getCharacters()));
        textBox.getChildren().addAll(textLabel, textField, testButton);
        textBox.setSpacing(10);
        centerPane.add(textBox,0,0);

        return new Scene(outerPane);
    }

    private void submit(CharSequence characters) {
        System.out.println(characters);
    }
}
