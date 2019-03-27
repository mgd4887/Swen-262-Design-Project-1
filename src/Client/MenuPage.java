package Client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuPage extends Page {

    public MenuPage(Client client) {
        super(client);
    }

    @Override
    public Scene getScene() {

        //create outer boarder pane
        BorderPane outerPane = new BorderPane();

        //create menu text
        Text menuText = new Text("Welcome\r\nselect a menu");
        menuText.setFont(Font.font("Verdana", 20));
        outerPane.setTop(menuText);

        //create center grid pane of options
        GridPane centerPane = new GridPane();
        outerPane.setCenter(centerPane);

        //create button for time page
        HBox timeBox = new HBox();
        Button timeButton = new Button("Time");
        timeButton.setOnMouseClicked(mouseEvent -> client.changePage(new TimePage(client)));
        timeBox.getChildren().addAll(timeButton);


        //create button for visit page
        HBox visitBox = new HBox();
        Button visitButton = new Button("Visit");
        visitButton.setOnMouseClicked(mouseEvent -> client.changePage(new VisitPage(client)));
        visitBox.getChildren().addAll(visitButton);

        //create button for store page
        HBox storeBox = new HBox();
        Button storeButton = new Button("Store");
        storeButton.setOnMouseClicked(mouseEvent -> client.changePage(new StorePage(client)));
        storeBox.getChildren().addAll(storeButton);

        //create button for borrow page
        HBox borrowBox = new HBox();
        Button borrowButton = new Button("Borrow");
        borrowButton.setOnMouseClicked(mouseEvent -> client.changePage(new BorrowPage(client)));
        borrowBox.getChildren().addAll(borrowButton);

        //create button for Status page
        HBox statusBox = new HBox();
        Button statusButton = new Button("Store");
        statusButton.setOnMouseClicked(mouseEvent -> client.changePage(new StatusPage(client)));
        statusBox.getChildren().addAll(statusButton);

        //add buttons to gridpane
        centerPane.add(timeBox,0,0);
        centerPane.add(visitBox,0,1);
        centerPane.add(storeBox,1,0);
        centerPane.add(borrowBox,1,1);
        centerPane.add(statusBox,2,0);
        centerPane.setVgap(10);
        centerPane.setHgap(10);

        return new Scene(outerPane);
    }

}
