package Request;

import java.util.ArrayList;
import java.util.List;

public class PayFine implements Request {
    private String visitorID;
    private int amount;

    public PayFine(String _clientId, int _amount, String _visitorID){
        visitorID = _visitorID;
        amount = _amount;
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