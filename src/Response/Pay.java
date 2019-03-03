package Response;

import Request.PayFine;
import Request.Problem;
import java.util.List;

public class Pay implements Response{
    public String perform(List<String> params){

        String clientId = params.remove(0);

        if(params.size() < 1){
            return String.format("%s,pay,missing-parameters,amount");
        }

        PayFine pf = null;

        int amount = Integer.parseInt(params.get(0));

        if(params.size() == 1){
            return null;
        } else {
            String visitorId = params.get(1);
        }

        List<Object> result = pf.ExecuteCommand();

        if(result.get(0) instanceof Problem){
            return String.format("%s,pay,%s;",clientId,result.get(0));
        }

//        LBMS.ur.pushToDone(clientId,pf);
        int remainingFine = (Integer) result.get(0);
        return String.format("%s,pay,$%d;",clientId,remainingFine);
    }
}