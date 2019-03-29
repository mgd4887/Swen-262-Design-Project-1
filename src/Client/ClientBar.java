package Client;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import system.SerializedLibraryBookManagementSystem;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientBar extends Page{

    ArrayList<Client> clients = new ArrayList <Client>();

    public ClientBar(ClientApplication clientApplication, SerializedLibraryBookManagementSystem LBMS) {
        super(clientApplication, LBMS);
    }

    @Override
    public Node getRoot() {
        HBox root = new HBox();
        ArrayList<Button> clientButtons = new ArrayList <>();
        for (Client client: clients){
            Button clientButton = new Button(client.getName());
            clientButton.setOnMouseClicked(event -> clientApplication.changeClient(client.getID()));
            clientButtons.add(clientButton);

        }
        root.getChildren().addAll(clientButtons);

        Button addClientButton = new Button("+");
        addClientButton.setOnMouseClicked(event -> addClient());

        root.getChildren().add(addClientButton);
        return root;
    }

    private void addClient() {
        String response = LBMS.performRequest("connect;");
        String regex = "connect,([0-9]+);";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(response);
        if(matcher.matches()){
            int ID = Integer.parseInt(matcher.group(1));
            clientApplication.changePage(new NewClientPage(clientApplication,LBMS, ID));
            Client current = new Client(ID);
            clients.add(current);
            clientApplication.setCurrentClient(current);
            clientApplication.refresh();
        }else{
            clientApplication.addError(response);
            //TODO handle
        }
    }
}
