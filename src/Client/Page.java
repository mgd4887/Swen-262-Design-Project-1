package Client;

import javafx.scene.Node;
import system.SerializedLibraryBookManagementSystem;

public abstract class Page {
    protected final SerializedLibraryBookManagementSystem LBMS;
    protected final ClientApplication clientApplication;

    public Page(ClientApplication clientApplication, SerializedLibraryBookManagementSystem LBMS) {
        this.clientApplication = clientApplication;
        this.LBMS = LBMS;
    }

    public abstract Node getRoot();

}
