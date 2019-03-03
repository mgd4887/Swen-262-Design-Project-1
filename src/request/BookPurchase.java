package request;

import java.util.ArrayList;
import java.util.List;

public class BookPurchase implements Request {
    public String clientId;
    public int quantity;
    public List<Integer> books;

    public BookPurchase(String _clientId, int _quantity, List<Integer> _books) {
        clientId = _clientId;
        quantity = _quantity;
        books = _books;
    }

    @Override
    public List<Object> ExecuteCommand() {

        List<Object> output = new ArrayList<Object>();
            return null;
    }
    @Override
    public List<Object> UndoCommand(){
        List<Object> output = new ArrayList<>();

          return output;
    }


}