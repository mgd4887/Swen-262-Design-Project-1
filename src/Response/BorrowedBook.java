package Response;

import Request.FindBorrowedBooks;
import Request.Problem;
import java.util.List;

public class BorrowedBook implements Response {

    public String perform(List<String> params) {

        String clientId = params.remove(0);

        String visitorId;

        if(params.isEmpty()){
            return null;
        } else {
            visitorId = params.get(0);
        }

        List<Object> results = new FindBorrowedBooks(clientId,visitorId).ExecuteCommand();

        if((!results.isEmpty())&& results.get(0) instanceof Problem){
            return String.format("%s,borrowed,%s",clientId,results.get(0));
        }

        String output = String.format("%s,borrowed,%d",clientId,results.size());

        for(int i = 0; i < results.size(); i++) {
            Borrow b = (Borrow) results.get(i);
        }

        return output;
    }
}