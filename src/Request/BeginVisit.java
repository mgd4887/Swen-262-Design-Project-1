package Request;

import java.util.ArrayList;
import java.util.List;

public class BeginVisit implements Request{
    String visitorID;

    public BeginVisit(String _visitorID){
        visitorID = _visitorID;
    }

   @Override
    public List<Object> ExecuteCommand(){
        List<Object> output = new ArrayList<>();


        return output;
    }
    @Override
    public List<Object> UndoCommand() {
        List<Object> output = new ArrayList<>();
        return output;
    }

}