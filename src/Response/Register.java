package Response;

import java.util.ArrayList;
import java.util.List;
import Request.Problem;
import Request.RegisterVisitor;

public class Register implements Response {
    public String perform(List<String> params){

        String clientId = params.remove(0);


        if(params.size() < 4) {
            List<String> requiredParams = new ArrayList<>();
            requiredParams.add("first name");
            requiredParams.add("last name");
            requiredParams.add("address");
            requiredParams.add("phone number");

            return clientId +",register," + MissingParameters.missingParameters(requiredParams, params.size());
        }

        List<Object> output = new RegisterVisitor(params.get(0), params.get(1), params.get(2), params.get(3)).ExecuteCommand();
        if(output.get(0) instanceof Problem){
            return String.format(clientId +",register,%s;",((Problem) output.get(0)).getType());
        }
        return null;


    }
}