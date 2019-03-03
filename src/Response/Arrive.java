package Response;

import Request.BeginVisit;
import Request.Problem;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public class Arrive implements Response {
    public String perform(List<String> params){

        String clientId = params.remove(0);

        BeginVisit bv;
        if(params.size() < 1){
            return null;
        } else{
            bv = new BeginVisit(params.get(0));
        }

        List<Object> results = bv.ExecuteCommand();

        if(results.get(0) instanceof Problem){
            return String.format("%s,arrive,%s;",clientId,((Problem) results.get(0)).getType());
        }

        String id = (String) results.get(0);
        LocalDate visitDate = (LocalDate) results.get(1);
        LocalTime visitTime = (LocalTime) results.get(2);

        return String.format("%s,arrive,%s,%02d/%02d/%d,%02d:%02d:%02d;",clientId,id,visitDate.getDayOfMonth(),
                visitDate.getMonthValue(), visitDate.getYear(), visitTime.getHour(), visitTime.getMinute(),
                visitTime.getSecond());
    }
}