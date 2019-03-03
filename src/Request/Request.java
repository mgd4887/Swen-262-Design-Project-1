package Request;

import java.util.List;

public interface Request {
    List<Object> ExecuteCommand();
    List<Object> UndoCommand();
}