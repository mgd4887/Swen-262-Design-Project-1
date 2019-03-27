package Client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class TimePage extends Page {

    public TimePage(Client client) {
        super(client);
    }

    @Override
    public Scene getScene() {
        //create an element to forward time
        HBox forwardTimeBox = new HBox();
        Button forwardTimeButton = new Button("Advance Time");
        TextField forwardAmount = new TextField("Enter amount of time to forward here");
        forwardAmount.setPrefWidth(300);
        forwardAmount.setMinWidth(300);
        forwardTimeButton.setOnMouseClicked(mouseEvent -> this.forwardTime(forwardAmount.getCharacters()));
        forwardTimeBox.getChildren().addAll(forwardTimeButton, forwardAmount);
        forwardTimeBox.setSpacing(10);
        //forwardTimeBox.setPrefWidth(250);

        //create an element to get the current time
        HBox currentTimeBox = new HBox();
        Button getTimeButton = new Button("Get Current Time");
        Label currentTimeLabel = new Label("Current Time:");
        Text currentTime = new Text();
        getTimeButton.setOnMouseClicked(mouseEvent -> this.getCurrentTime());
        currentTimeBox.getChildren().addAll(currentTimeLabel, currentTime, getTimeButton);
        currentTimeBox.setSpacing(10);
        currentTimeBox.setPrefWidth(250);

        //create a return to home button
        HBox returnBox = new HBox();
        Button returnButton = new Button("Return to menu");
        returnButton.setOnMouseClicked(mouseEvent -> client.changePage(new MenuPage(client)));
        returnBox.getChildren().addAll(returnButton);
        returnBox.setSpacing(10);
        returnBox.setPrefWidth(250);


        GridPane center = new GridPane();
        center.add(forwardTimeBox,0,0);
        center.add(currentTimeBox,0,1);
        center.add(returnBox, 0, 2);
        center.setVgap(10);

        BorderPane root = new BorderPane();
        root.setCenter(center);
        return new Scene(root);
    }

    private void getCurrentTime() {

    }

    private void forwardTime(CharSequence characters) {

    }
}
