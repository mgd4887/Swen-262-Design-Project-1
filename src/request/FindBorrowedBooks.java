package request;

import java.util.ArrayList;
import java.util.List;

public class FindBorrowedBooks implements Request{
    private String clientID;
    private String visitorID;

    public FindBorrowedBooks(String _clientID, String _visitorID){
        clientID = _clientID;
        visitorID = _visitorID;
    }

    @Override
    public List<Object> ExecuteCommand(){

        List<Object> output = new ArrayList<>();
        return output;
    }

    @Override
    public List<Object> UndoCommand(){
        List<Object> output = new ArrayList<>();
        output.add(new Problem("cannot-undo", "The most recently executed command cannot be undone."));
        return output;
    }
}