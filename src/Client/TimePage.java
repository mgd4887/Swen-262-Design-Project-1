package Client;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import system.SerializedLibraryBookManagementSystem;


public class TimePage extends Page {

    /**
     * constructor for all pages
     * creates a page
     *
     * @param clientApplication the client that this page is in
     * @param LBMS              the LBMS the client is connected to
     */
    public TimePage(ClientApplication clientApplication, SerializedLibraryBookManagementSystem LBMS) {
        super(clientApplication, LBMS);
    }

    @Override
    public Node getRoot() {
        //create an element to forward time
        HBox forwardTimeBox = new HBox();
        Button forwardTimeButton = new Button("Advance Time");
        TextField forwardAmountDays = new TextField("Days");
        TextField forwardAmountHours = new TextField("Hours");
        forwardTimeButton.setOnMouseClicked(mouseEvent -> this.forwardTime(forwardAmountDays.getCharacters(), forwardAmountHours.getCharacters()));
        forwardTimeBox.getChildren().addAll(forwardTimeButton, forwardAmountDays, forwardAmountHours);
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
        returnButton.setOnMouseClicked(mouseEvent -> clientApplication.changePage(new MenuPage(clientApplication, LBMS)));
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
        return (root);
    }

    private void getCurrentTime() {

    }

    private void forwardTime(CharSequence daysSequence, CharSequence hourSequence) {
        String daysString = daysSequence.toString();
        String hoursString = hourSequence.toString();
        int days = 0;
        int hours = 0;
        boolean invalid = false;
        try{
            days = Integer.parseInt(daysString);
        }catch (Exception e){
            invalid = true;
            System.out.println("could not pasre day");
        }
        try {
            hours = Integer.parseInt(hoursString);
        }catch (Exception e){
            invalid = true;
            System.out.println("could not pasre hour");
        }


        //TODO connect to server
        if (!invalid) {
            System.out.println("advance," + days + "," + hours + ";");
        }
    }
}
