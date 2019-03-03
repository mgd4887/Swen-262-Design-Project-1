package request;

import java.util.ArrayList;
import java.util.List;

public class ReturnBook implements Request {
    String clientId;
    String visitorId;
    List<Integer> bookIds;

    public ReturnBook(String _clientId, String _visitorId, List<Integer> _bookIds){
        clientId = _clientId;
        visitorId = _visitorId;
        bookIds = _bookIds;
    }

    public ReturnBook(String _clientId, List<Integer> _bookIds){
        clientId = _clientId;
        bookIds = _bookIds;
    }

    @Override
    public List<Object> ExecuteCommand(){

        List<Object> output = new ArrayList<>();
        return output;
    }

    @Override
    public List<Object> UndoCommand(){
        List<Object> output = new ArrayList<>();
        for (int id : bookIds) {
        }

        output.add("success");
        return output;
    }

}