package Response;

import Request.BookPurchase;
import Request.Problem;
import java.util.ArrayList;
import java.util.List;

public class Purchase implements Response {
    public String perform(List<String> params) {
        String clientId = params.remove(0);

        if (params.size() < 2) {
            List<String> requiredParameters = new ArrayList<>();
            requiredParameters.add("quantity");
            requiredParameters.add("id");
            return MissingParameters.missingParameters(requiredParameters, params.size());
        }

        int quantity = Integer.parseInt(params.remove(0));

        List<Integer> ids = new ArrayList<>();

        for (String p : params) {
            ids.add(Integer.parseInt(p));
        }

        BookPurchase bp = new BookPurchase(clientId, quantity, ids);
        List<Object> result = bp.ExecuteCommand();

        if (!(result.isEmpty()) && result.get(0) instanceof Problem) {
            return String.format("%s,buy,%s;", clientId, result.get(0));
        }
        return null;
    }

}

