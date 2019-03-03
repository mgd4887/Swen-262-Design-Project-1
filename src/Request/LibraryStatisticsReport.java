package Request;

import java.util.ArrayList;
import java.util.List;

public class LibraryStatisticsReport implements Request{

    private int days;

    public LibraryStatisticsReport(int _days){
        days = _days;
    }

    @Override
    public List<Object> ExecuteCommand() {
        return null;
    }

    @Override
    public List<Object> UndoCommand(){
        List<Object> output = new ArrayList<>();
        output.add(new Problem("cannot-undo", "The most recently executed command cannot be undone."));
        return output;
    }
}