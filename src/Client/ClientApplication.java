package Client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import system.SerializedLibraryBookManagementSystem;

import java.util.Observable;
import java.util.Observer;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientApplication extends Application implements Observer {

    private Page page;
    private Stage mainStage;
    private BorderPane root;
    private int clientID;
    private SerializedLibraryBookManagementSystem LBMS;
    private ClientBar clientBar;
    private Client curretClient;

    public static void main(String[] args) {
        // Create the system.
        Application.launch( args );
    }

    @Override
    public void start(Stage mainStage) throws Exception {
        this.LBMS = SerializedLibraryBookManagementSystem.loadFromFile();
        this.mainStage = mainStage;
        this.page = new StartPage(this, LBMS);
        this.root = new BorderPane();
        this.clientBar = new ClientBar(this, LBMS);
        this.root.setTop(clientBar.getRoot());
        this.mainStage.setScene(new Scene(root));
        refresh();
        //Scene scene = this.page.getRoot();
        //this.mainStage.setScene( scene );
        //mainStage.show();
    }

    @Override
    public void update(Observable observable, Object o) {
        javafx.application.Platform.runLater(this::refresh);
    }

    public void refresh() {
        if (curretClient != null) {
            Page page = curretClient.getCurerntPage();
            if (page != null) {
                root.setCenter(page.getRoot());
            } else {
                root.setCenter(null);
            }
        }else{
            if (page != null) {
                root.setCenter(page.getRoot());
            } else {
                root.setCenter(null);
            }
        }
        root.setPrefWidth(1280);
        root.setPrefHeight(720);
        root.setTop(clientBar.getRoot());
        root.setRight(null); // remove any errors
        //this.mainStage.setScene(scene);
        mainStage.show();

    }

    /**
     * changes the current page of hte current client
     * @param page the page to the change the current client to
     */
    public void changePage(Page page) {
        curretClient.changePage(page);
        this.page = curretClient.getCurerntPage();
        this.refresh();
    }

    /**
     * change the current client
     * @param clientID the ID of the client to change to
     */
    public void changeClient(int clientID) {
        this.clientID = clientID;
        this.curretClient = clientBar.getClient(clientID);
        this.refresh();
    }

    /**
     * returns the current client ID
     * @return the ID of  hte current client
     */
    public int currentClientID(){
        return clientID;
    }

    /**
     * gets the current Client
     * @return the current client
     */
    public Client getCurrentClient() {
        return curretClient;
    }

    /**
     * adds an error to the client
     * @param response the error message
     */
    public void addError(String response) {
        VBox error = new VBox();
        Text errorText = new Text(response);
        error.getChildren().add(errorText);
        root.setRight(error);
    }

    /**
     * logs the current client out off the session
     */
    public void logout() {
        javafx.application.Platform.runLater(clientBar::logOut);
    }

    /**
     * disconnects the  client of the given ID
     * @param id the ID of the client to disconnect
     */
    public void disconnect(int id) {
        clientBar.disconnect(id);
    }
}
