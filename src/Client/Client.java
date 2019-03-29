package Client;

public class Client {
    private int ID;
    private String firstName;
    private String lastName;
    private Page curerntPage;
    private String username;

    public Client(int ID) {
        this.ID = ID;
    }

    public String getName() {
        if (firstName == null || lastName == null){
            return Integer.toString(ID);
        }else{
            return firstName + " " + lastName;
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String username) {
        this.username = username;
    }

    public Page getCurerntPage(){
        return curerntPage;
    }

    public void changePage(Page page) {
        this.curerntPage = page;
    }
}
