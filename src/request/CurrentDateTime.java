package request;

import java.util.ArrayList;
import java.util.List;



public class CurrentDateTime implements Request {

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