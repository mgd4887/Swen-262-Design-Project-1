package request;

import java.util.ArrayList;
import java.util.List;


public class BorrowBook implements Request {

    private String clientId;
    private List<Integer> bookIds;
    private String visitorId;

    public BorrowBook(String _clientId, List<Integer> _bookIds){
        clientId = _clientId;
        bookIds = _bookIds;
    }

    public BorrowBook(String _clientId, List<Integer> _bookIds, String _visitorId){
        clientId = _clientId;
        bookIds = _bookIds;
        visitorId = _visitorId;
    }
    @Override
    public List<Object> ExecuteCommand(){

        List<Object> output = new ArrayList<>();
        return output;
    }
    @Override
    public List<Object> UndoCommand(){
        List<Object> output = new ArrayList<>();
        return output;
    }
}