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
        this.page = null;
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
            root.setCenter(null);
        }
        root.setTop(clientBar.getRoot());
        root.setRight(null); // remove any errors
        //this.mainStage.setScene(scene);
        mainStage.show();

    }

    public void changePage(Page page) {
        curretClient.changePage(page);
        this.page = curretClient.getCurerntPage();
        this.refresh();
    }

    public void changeClient(int clientID) {
        this.clientID = clientID;
        this.curretClient = clientBar.getClient(clientID);
    }

    public int currentClientID(){
        return clientID;
    }

    public Client getCurrentClient() {
        return curretClient;
    }

    public void addError(String response) {
        VBox error = new VBox();
        Text errorText = new Text(response);
        error.getChildren().add(errorText);
        root.setRight(error);
    }

    public void setCurrentClient(Client current) {
        this.curretClient = current;
    }

    public void logout() {
        javafx.application.Platform.runLater(clientBar::logOut);
    }
}
