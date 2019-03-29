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

    private ArrayList<Client> clients = new ArrayList <Client>();

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
            Client current = new Client(ID);
            clients.add(current);
            clientApplication.setCurrentClient(current);
            clientApplication.changePage(new LoginPage(clientApplication,LBMS, ID));
            clientApplication.refresh();
        }else{
            clientApplication.addError(response);
            //TODO handle
        }
    }

    public void logOut() {
        int id = clientApplication.getCurrentClient().getID();
        String response = LBMS.performRequest(id+",logout;");
        clientApplication.changePage(new LoginPage(clientApplication,LBMS,id));

    }

    public Client getClient(int clientID) {
        for (int i = 0; i < clients.size(); i++){
            if (clients.get(i).getID() == clientID){
                return clients.get(i);
            }
        }
        return null;
    }
}
