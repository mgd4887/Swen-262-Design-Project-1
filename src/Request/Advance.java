package Request;

import java.util.ArrayList;
import java.util.List;

public class Advance implements Request {

    public int numHours;

    public Advance(int _numDays, int _numHours){
        numHours = 24 * _numDays + _numHours;
    }

    @Override
    public List<Object> ExecuteCommand(){

        return new ArrayList<Object>();
    }

    @Override
    public List<Object> UndoCommand(){
        List<Object> output = new ArrayList<>();
        output.add(new Problem("cannot-undo", "The most recently executed command cannot be undone."));
        return output;
    }



}