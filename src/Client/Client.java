package Client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class Client extends Application implements Observer {

    private Page page;
    private Stage mainStage;
    private BorderPane root;

    public static void main(String[] args) {
        Application.launch( args );
    }

    @Override
    public void start(Stage mainStage) throws Exception {
        this.mainStage = mainStage;
        this.page = new MenuPage(this);
        this.root = new BorderPane();
        refresh();
        //Scene scene = this.page.getScene();
        //this.mainStage.setScene( scene );
        //mainStage.show();
    }

    @Override
    public void update(Observable observable, Object o) {
        javafx.application.Platform.runLater(this::refresh);
    }

    private void refresh() {
        Scene scene = this.page.getScene();
        this.mainStage.setScene(scene);
        mainStage.show();

    }

    public void changePage(Page page) {
        this.page = page;
        this.refresh();
    }
}
