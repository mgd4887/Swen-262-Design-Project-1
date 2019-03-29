package Client;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import system.SerializedLibraryBookManagementSystem;

public class MenuPage extends Page {

    /**
     * constructor for all pages
     * creates a page
     *
     * @param clientApplication the client that this page is in
     * @param LBMS              the LBMS the client is connected to
     */
    public MenuPage(ClientApplication clientApplication, SerializedLibraryBookManagementSystem LBMS) {
        super(clientApplication, LBMS);
    }

    @Override
    public Node getRoot() {

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
        timeButton.setOnMouseClicked(mouseEvent -> clientApplication.changePage(new TimePage(clientApplication, LBMS)));
        timeBox.getChildren().addAll(timeButton);


        //create button for visit page
        HBox visitBox = new HBox();
        Button visitButton = new Button("Visit");
        visitButton.setOnMouseClicked(mouseEvent -> clientApplication.changePage(new VisitPage(clientApplication, LBMS)));
        visitBox.getChildren().addAll(visitButton);

        //create button for store page
        HBox storeBox = new HBox();
        Button storeButton = new Button("Store");
        storeButton.setOnMouseClicked(mouseEvent -> clientApplication.changePage(new StorePage(clientApplication, LBMS)));
        storeBox.getChildren().addAll(storeButton);

        //create button for borrow page
        HBox borrowBox = new HBox();
        Button borrowButton = new Button("Borrow");
        borrowButton.setOnMouseClicked(mouseEvent -> clientApplication.changePage(new BorrowPage(clientApplication, LBMS)));
        borrowBox.getChildren().addAll(borrowButton);

        //create button for Status page
        HBox statusBox = new HBox();
        Button statusButton = new Button("Status");
        statusButton.setOnMouseClicked(mouseEvent -> clientApplication.changePage(new StatusPage(clientApplication, LBMS)));
        statusBox.getChildren().addAll(statusButton);

        //create logout button
        HBox logoutBox = new HBox();
        Button logoutButton = new Button("Logout");
        logoutButton.setOnMouseClicked(mouseEvent -> clientApplication.logout());
        logoutBox.getChildren().addAll(logoutButton);

        //create button for Register page
        HBox registerBox = new HBox();
        Button registerButton = new Button("Register new user");
        registerButton.setOnMouseClicked(mouseEvent -> clientApplication.changePage(new RegisterVisitorPage(clientApplication, LBMS)));
        registerBox.getChildren().addAll(registerButton);

        //add buttons to gridpane
        centerPane.add(timeBox,0,0);
        centerPane.add(visitBox,0,1);
        centerPane.add(storeBox,1,0);
        centerPane.add(borrowBox,1,1);
        centerPane.add(statusBox,2,0);
        centerPane.add(logoutBox,2,1);
        centerPane.add(registerBox,3,0);
        centerPane.setVgap(10);
        centerPane.setHgap(10);

        return (outerPane);
    }

}
